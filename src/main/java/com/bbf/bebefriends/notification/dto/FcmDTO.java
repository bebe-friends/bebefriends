package com.bbf.bebefriends.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
            @NotNull
            Long hotdealId,

            @Schema(description = "핫딜 관련 나이 (대상 나이 필터 조건)", example = "[1, 2, 3]")
            @NotNull
            @Size(min = 1, message = "적어도 하나 이상의 나이를 제공해야 합니다.")
            List<Integer> ages,

            @Schema(description = "핫딜 관련 카테고리 (대상 카테고리 필터 조건)", example = "[\"가구\"]")
            @NotNull
            @Size(min = 1, message = "적어도 하나 이상의 카테고리를 제공해야 합니다.")
            List<String> categories,

            @Schema(description = "야간 여부 (true: 야간, false: 주간)", example = "true")
            boolean isNight,

            @Schema(description = "알림 제목", example = "핫딜 알림 제목")
            @NotBlank
            String title,

            @Schema(description = "알림 본문", example = "핫딜 알림 본문 내용")
            @NotBlank
            String body

    ) {}
}
