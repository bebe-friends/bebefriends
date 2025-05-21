package com.bbf.bebefriends.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

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
}
