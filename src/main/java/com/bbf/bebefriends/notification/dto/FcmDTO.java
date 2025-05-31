package com.bbf.bebefriends.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class FcmDTO {

    @Schema(description = "푸시 알림 테스트")
    public record FcmTestRequest(

            @Schema(description = "FCM Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
            @NotBlank String token,

            @Schema(description = "알림 제목", example = "공지사항")
            @NotBlank String title,

            @Schema(description = "알림 내용", example = "앱 패치 관련 ...")
            @NotBlank String body
    ) {}

    @Schema(description = "핫딜 알림 요청 객체")
    public record HotdealNotificationRequest(

            @Schema(description = "핫딜 게시글 ID", example = "1")
            @NotBlank
            Long hotdealId,

            @Schema(description = "핫딜 관련 나이 (대상 나이 필터 조건)", example = "[1, 2, 3]")
            @NotBlank
            List<Integer> ages,

            @Schema(description = "핫딜 관련 카테고리 (대상 카테고리 필터 조건)", example = "[\"가구\"]")
            @NotBlank
            List<String> categories,

            @Schema(description = "야간 여부 (true: 야간, false: 주간)", example = "true")
            @NotBlank
            boolean isNight,

            @Schema(description = "알림 제목", example = "핫딜 알림 제목")
            @NotBlank
            String title,

            @Schema(description = "알림 본문", example = "핫딜 알림 본문 내용")
            @NotBlank
            String body

    ) {}
}
