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
        Map<String, Object> response = webClient.get()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakao_account");

        return KakaoUserInfo.builder()
                .email((String) kakaoAccount.get("email"))
                .phone((String) kakaoAccount.get("phone_number"))
                .build();
    }
}