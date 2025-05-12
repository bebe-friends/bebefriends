package com.bbf.bebefriends.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class UserDTO {

    @Schema(description = "로그인 요청")
    public record UserLoginRequest(
            @Schema(description = "Kakao oauth Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
            @NotBlank String oauthToken
    ) {}

    @Schema(description = "로그인 및 가입 시 응답")
    public record UserAccessResponse(
            @Schema(description = "Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
            @NotBlank String accessToken
    ) {}

    @Schema(description = "회원가입 요청")
    public record UserSignupRequest(

            @Schema(description = "Kakao oauth Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
            @NotBlank String oauthToken,

            @Schema(description = "핫딜 푸시 알림 동의 여부", example = "true")
            @NotBlank boolean hotDealNotification,

            @Schema(description = "야간 핫딜 푸시 알림 동의 여부", example = "false")
            @NotBlank boolean nightHotDealNotification,

            @Schema(description = "댓글 푸시 알림 동의 여부", example = "true")
            @NotBlank boolean commentNotification,

            @Schema(description = "마케팅 푸시 알림 동의 여부", example = "true")
            @NotBlank boolean marketingNotification,

            @Schema(description = "마케팅 푸시 알림 동의 여부", example = "true")
            @NotBlank boolean nightMarketingNotification,

            @Schema(description = "FCM 디바이스 토큰", example = "djsd82390sdjqkwej09832k-dsdk")
            @NotBlank String fcmToken,

            @Schema(description = "약관 동의 시간", example = "2023-11-01T12:00:00")
            @NotBlank LocalDateTime agreement,

            @Schema(description = "개인정보 처리방침 동의 시간", example = "2023-11-01T12:00:00")
            @NotBlank LocalDateTime privatePolicy,

            @Schema(description = "만 나이 동의 여부", example = "true")
            @NotBlank Boolean age
    ) {}

    @Schema(description = "이용자 알림 설정")
    public record UpdateNotificationSettingsRequest(
            @NotBlank
            @Schema(description = "핫딜 알림 동의 여부", example = "true")
            boolean hotDealNotification,

            @NotBlank
            @Schema(description = "핫딜 야간 알림 동의 여부", example = "false")
            boolean hotDealNightNotification,

            @NotBlank
            @Schema(description = "마케팅 알림 동의 여부", example = "true")
            boolean marketingNotification,

            @NotBlank
            @Schema(description = "마케팅 야간 알림 동의 여부", example = "true")
            boolean marketingNightNotification,

            @NotBlank
            @Schema(description = "댓글 알림 동의 여부", example = "false")
            boolean commentNotification
    ) {
    }

    @Schema(description = "이용자 알림 설정")
    public record UpdateNotificationSettingsResponse(
            @NotBlank
            @Schema(description = "핫딜 알림 동의 여부", example = "true")
            boolean hotDealNotification,

            @NotBlank
            @Schema(description = "핫딜 야간 알림 동의 여부", example = "false")
            boolean hotDealNightNotification,

            @NotBlank
            @Schema(description = "마케팅 알림 동의 여부", example = "true")
            boolean marketingNotification,

            @NotBlank
            @Schema(description = "마케팅 야간 알림 동의 여부", example = "true")
            boolean marketingNightNotification,

            @NotBlank
            @Schema(description = "댓글 알림 동의 여부", example = "false")
            boolean commentNotification
    ) {
    }

    @Schema(description = "이용자 동의 요청")
    public record UpdateTermsAgreementsRequest(
            @Schema(description = "약관 동의 시간", example = "2023-11-01T12:00:00")
            LocalDateTime agreement,

            @Schema(description = "개인정보 처리방침 동의 시간", example = "2023-11-01T12:00:00")
            LocalDateTime privatePolicy,

            @Schema(description = "만 나이 동의 여부", example = "true")
            Boolean age
    ) {
    }

    @Schema(description = "이용자 동의 정보 확인")
    public record UserTermsAgreementsResponse(
            @Schema(example = "2025-05-03T15:49:44.319Z", description = "약관 동의 시간")
            LocalDateTime agreement,

            @Schema(example = "2025-05-03T15:49:44.319Z", description = "개인정보 처리방침 동의 시간")
            LocalDateTime privatePolicy,

            @Schema(example = "true", description = "만 나이 동의 여부")
            Boolean age
    ) {
    }
}
