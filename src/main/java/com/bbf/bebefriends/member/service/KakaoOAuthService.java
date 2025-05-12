package com.bbf.bebefriends.member.service;

import com.bbf.bebefriends.member.dto.KakaoUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class KakaoOAuthService {

    private final WebClient webClient;

    public KakaoOAuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://kapi.kakao.com").build();
    }

    public KakaoUserInfo getUserInfo(String accessToken) {
        return webClient.get()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(Map.class);
                    } else {
                        // 요청 실패 시 로그 출력
                        System.err.println("Kakao API response status: " + response.statusCode());
                        return response.bodyToMono(Map.class).doOnNext(body -> {
                            System.err.println("Response body: " + body);
                        });
                    }
                })
                .map(response -> {
                    Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakao_account");
                    return KakaoUserInfo.builder()
                            .email((String) kakaoAccount.get("email"))
                            .phone((String) kakaoAccount.get("phone_number"))
                            .build();
                })
                .block();
    }
}