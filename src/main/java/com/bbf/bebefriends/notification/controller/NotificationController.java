package com.bbf.bebefriends.notification.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.notification.dto.NotificationDTO;
import com.bbf.bebefriends.notification.entity.NotificationType;
import com.bbf.bebefriends.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "알림 조회", description = "사용자 별 알림 조회")
@RequestMapping("/api/v1/users/notifications")
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 전체 조회", description = "현재 유저의 전체 알림 조회")
    @GetMapping
    public BaseResponse<Page<NotificationDTO.NotificationResponse>> getUserNotifications(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return BaseResponse.onSuccess(
                notificationService.getNotificationsByUser(user.getUserId(), page, size),
                ResponseCode.OK
        );
    }

    @Operation(summary = "타입별 알림 전체 조회", description = "현재 유저의 타입별 전체 알림 조회")
    @GetMapping("/type")
    public BaseResponse<Page<NotificationDTO.NotificationResponse>> getUserNotificationsByType(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam("type") NotificationType type,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return BaseResponse.onSuccess(
                notificationService.getNotificationsByType(user.getUserId(), type, page, size),
                ResponseCode.OK
        );
    }


    @Operation(summary = "알림 읽기 처리", description = "현재 유저의 알림 읽기 처리")
    @PatchMapping("/{notificationId}/read")
    public void markNotificationAsRead(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long notificationId
    ) {
        notificationService.markAsRead(user.getUserId(), notificationId);
    }


}