package com.bbf.bebefriends.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

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
            @NotNull boolean hotDealNotification,

            @Schema(description = "야간 핫딜 푸시 알림 동의 여부", example = "false")
            @NotNull boolean nightHotDealNotification,

            @Schema(description = "댓글 푸시 알림 동의 여부", example = "true")
            @NotNull boolean commentNotification,

            @Schema(description = "마케팅 푸시 알림 동의 여부", example = "true")
            @NotNull boolean marketingNotification,

            @Schema(description = "마케팅 푸시 알림 동의 여부", example = "true")
            @NotNull boolean nightMarketingNotification,

            @Schema(description = "FCM 디바이스 토큰", example = "djsd82390sdjqkwej09832k-dsdk")
            @NotBlank String fcmToken,

            @Schema(description = "약관 동의 시간", example = "2023-11-01T12:00:00")
            @NotNull LocalDateTime agreement,

            @Schema(description = "개인정보 처리방침 동의 시간", example = "2023-11-01T12:00:00")
            @NotNull LocalDateTime privatePolicy,

            @Schema(description = "만 나이 동의 여부", example = "true")
            @NotNull Boolean age
    ) {}

    @Schema(description = "유저 정보 조회 결과")
    public record UserInfoResponse(

            @NotNull
            @Schema(description = "유저 ID", example = "1")
            Long userId,

            @NotNull
            @Schema(description = "닉네임", example = "깜놀하마@f6dd")
            String nickname,

            @NotNull
            @Schema(description = "이메일", example = "test@naver.com")
            String email
    ) {}

    @Schema(description = "이용자 알림 설정")
    public record UpdateNotificationSettingsRequest(
            @NotNull
            @Schema(description = "핫딜 알림 동의 여부", example = "true")
            boolean hotDealNotification,

            @NotNull
            @Schema(description = "핫딜 야간 알림 동의 여부", example = "false")
            boolean hotDealNightNotification,

            @NotNull
            @Schema(description = "마케팅 알림 동의 여부", example = "true")
            boolean marketingNotification,

            @NotNull
            @Schema(description = "마케팅 야간 알림 동의 여부", example = "true")
            boolean marketingNightNotification,

            @NotNull
            @Schema(description = "댓글 알림 동의 여부", example = "false")
            boolean commentNotification
    ) {
    }

    @Schema(description = "이용자 알림 설정")
    public record UpdateNotificationSettingsResponse(
            @NotNull
            @Schema(description = "핫딜 알림 동의 여부", example = "true")
            boolean hotDealNotification,

            @NotNull
            @Schema(description = "핫딜 야간 알림 동의 여부", example = "false")
            boolean hotDealNightNotification,

            @NotNull
            @Schema(description = "마케팅 알림 동의 여부", example = "true")
            boolean marketingNotification,

            @NotNull
            @Schema(description = "마케팅 야간 알림 동의 여부", example = "true")
            boolean marketingNightNotification,

            @NotNull
            @Schema(description = "댓글 알림 동의 여부", example = "false")
            boolean commentNotification
    ) {
    }

    @Schema(description = "이용자 동의 요청")
    public record UpdateTermsAgreementsRequest(
            @NotNull
            @Schema(description = "약관 동의 시간", example = "2023-11-01T12:00:00")
            LocalDateTime agreement,

            @NotNull
            @Schema(description = "개인정보 처리방침 동의 시간", example = "2023-11-01T12:00:00")
            LocalDateTime privatePolicy,

            @NotNull
            @Schema(description = "만 나이 동의 여부", example = "true")
            Boolean age
    ) {
    }

    @Schema(description = "이용자 동의 정보 확인")
    public record UserTermsAgreementsResponse(
            @NotNull
            @Schema(example = "2025-05-03T15:49:44.319Z", description = "약관 동의 시간")
            LocalDateTime agreement,

            @NotNull
            @Schema(example = "2025-05-03T15:49:44.319Z", description = "개인정보 처리방침 동의 시간")
            LocalDateTime privatePolicy,

            @NotNull
            @Schema(example = "true", description = "만 나이 동의 여부")
            Boolean age
    ) {
    }

    @Schema(description = "이용자 핫딜 알림 설정")
    public record UserHotdealNotificationRequest(
            @NotNull
            boolean age_0,

            @NotNull
            boolean age_1,

            @NotNull
            boolean age_2,

            @NotNull
            boolean age_3,

            @NotNull
            boolean age_4,

            @NotNull
            boolean age_5,

            @NotNull
            boolean age_6,

            @NotNull
            boolean age_7,

            @Schema(example = "[10, 11]", description = "핫딜 카테고리 설정 값 (미확정..)")
            List<Long> categorys // String : [""여행 및 체험", "도서"], Long : [1]
    ) {
    }

    @Schema(description = "이용자 핫딜 알림 설정")
    public record UserHotdealNotificationResponse(
            @NotNull
            boolean age_0,

            @NotNull
            boolean age_1,

            @NotNull
            boolean age_2,

            @NotNull
            boolean age_3,

            @NotNull
            boolean age_4,

            @NotNull
            boolean age_5,

            @NotNull
            boolean age_6,

            @NotNull
            boolean age_7,

            @Schema(example = "[\"여행 및 체험\"]", description = "핫딜 카테고리 설정 값 (미확정..)")
            List<String> categorys
    ) {
    }
}
