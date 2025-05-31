package com.bbf.bebefriends.notification.dto;

import com.bbf.bebefriends.notification.entity.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class NotificationDTO {

    @Schema(description = "알림 조회 응답")
    public record NotificationResponse(
            @Schema(description = "알림 ID", example = "1")
            @NotNull Long id,

            @Schema(description = "알림 제목", example = "핫딜 알림")
            @NotNull String title,

            @Schema(description = "알림 내용", example = "새로운 핫딜이 등록되었습니다!")
            @NotNull String content,

            @Schema(description = "알림 종류", example = "HOTDEAL")
            @NotNull NotificationType type,

            @Schema(description = "알림 생성 날짜 및 시간", example = "2023-11-01T12:00:00")
            @NotNull LocalDateTime createdAt,

            @Schema(description = "읽음 여부", example = "false")
            @NotNull boolean isRead
    ) {}

    @Schema(description = "알림 목록 조회 요청")
    public record NotificationListRequest(
            @Schema(description = "알림 타입 (댓글, 공지, 이벤트, 핫딜)", example = "HOTDEAL", nullable = true)
            NotificationType type,

            @Schema(description = "페이지 번호 (0부터 시작)", example = "0")
            @NotNull int page,

            @Schema(description = "페이지 크기", example = "10")
            @NotNull int size
    ) {}
}