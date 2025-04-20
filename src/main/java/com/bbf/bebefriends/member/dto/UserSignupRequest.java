package com.bbf.bebefriends.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "회원가입 요청 DTO")
public record UserSignupRequest(

        @Schema(description = "Firebase ID Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        @NotBlank String idToken,

        @Schema(description = "닉네임", example = "babyBear01")
        @NotBlank String nickname,

        @Schema(description = "핫딜 푸시 알림 동의 여부", example = "true")
        boolean hotDealNotification,

        @Schema(description = "댓글 푸시 알림 동의 여부", example = "true")
        boolean commentNotification,

        @Schema(description = "야간 핫딜 푸시 알림 동의 여부", example = "false")
        boolean nightDealNotification,

        @Schema(description = "마케팅 푸시 알림 동의 여부", example = "true")
        boolean marketingNotification,

        @Schema(description = "FCM 디바이스 토큰", example = "djsd82390sdjqkwej09832k-dsdk")
        @NotBlank String fcmToken

) {}
