package com.bbf.bebefriends.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmService {
    
    public void sendPushNotification(String token, String title, String body) {
        try {
            // 메시지 빌드
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            // 메시지 전송
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("푸시 알림 성공적으로 발송됨: " + response);

        } catch (Exception e) {
            System.err.println("푸시 알림 발송 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}