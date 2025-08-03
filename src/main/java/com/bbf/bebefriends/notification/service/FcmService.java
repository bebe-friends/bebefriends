package com.bbf.bebefriends.notification.service;

import com.google.firebase.messaging.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FcmService {

    /**
     * 단일 토큰에 푸시 알림 발송
     *
     * @param token FCM 토큰
     * @param title 알림 제목
     * @param body  알림 내용
     */
    public void sendPushNotification(String token, String title, String body) {
        try {
            if (token == null || token.isBlank()) {
                log.warn("토큰이 제공되지 않았습니다. 푸시 알림이 발송되지 않습니다.");
                return;
            }

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("푸시 알림 성공적으로 발송됨: {}", response);

        } catch (Exception e) {
            log.error("푸시 알림 발송 중 오류 발생: {}", e.getMessage());
        }
    }

    /**
     * 다중 토큰에 푸시 알림 발송 (Multicast)
     *
     * @param tokens FCM 토큰 리스트
     * @param title  알림 제목
     * @param body   알림 내용
     * @return BatchResponse (FCM 전송 결과)
     */
    public BatchResponse sendMulticastMessage(List<String> tokens, String title, String body) {
        if (tokens == null || tokens.isEmpty()) {
            log.warn("토큰 리스트가 비어 있습니다. 멀티캐스트 푸시 알림이 발송되지 않습니다.");
            return null;
        }

        try {
            MulticastMessage message = MulticastMessage.builder()
                    .addAllTokens(tokens)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            log.info("푸시 알림 성공적으로 발송됨: {}개의 요청 처리 (성공: {}, 실패: {})",
                    response.getResponses().size(), response.getSuccessCount(), response.getFailureCount());

            return response;

        } catch (Exception e) {
            log.error("멀티캐스트 푸시 알림 발송 중 오류 발생: {}", e.getMessage());
            return null;
        }
    }

    /**
     * FCM 전송 결과에서 실패한 토큰 추출
     *
     * @param response BatchResponse (멀티캐스트 응답)
     * @param tokens   전송 요청에 사용된 토큰 리스트
     * @return FcmTokenResult (실패한 토큰 리스트)
     */
    public FcmTokenResult extractFailedTokens(BatchResponse response, List<String> tokens) {
        if (response == null || tokens == null || tokens.isEmpty()) {
            log.warn("BatchResponse 또는 FCM 토큰 리스트가 비어 있습니다.");
            return new FcmTokenResult(new ArrayList<>());
        }

        List<String> failedTokens = new ArrayList<>();

        // 응답 리스트에서 실패한 토큰 추출
        List<SendResponse> responses = response.getResponses();
        for (int i = 0; i < responses.size(); i++) {
            SendResponse sendResponse = responses.get(i);

            if (!sendResponse.isSuccessful()) {
                String failedToken = tokens.get(i);
                failedTokens.add(failedToken);

                log.error("토큰 '{}' 전송 실패: {}", failedToken, sendResponse.getException().getMessage());
            }
        }

        return new FcmTokenResult(failedTokens);
    }

    /**
     * FCM 응답 결과 캡슐화 클래스
     */
    @Data
    public static class FcmTokenResult {
        private final List<String> removeTokens;

        public FcmTokenResult(List<String> removeTokens) {
            this.removeTokens = removeTokens != null ? removeTokens : new ArrayList<>();
        }
    }
}