package com.bbf.bebefriends.notification.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.notification.entity.Notification;
import com.bbf.bebefriends.notification.entity.NotificationType;
import com.bbf.bebefriends.notification.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public BaseResponse<Page<Notification>> getUserNotifications(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return BaseResponse.onSuccess(
                notificationService.getNotificationsByUser(user.getUserId(), page, size),
                ResponseCode.OK
        );
    }

    @GetMapping("/type")
    public BaseResponse<Page<Notification>> getUserNotificationsByType(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam("type") NotificationType type,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return BaseResponse.onSuccess(
                notificationService.getNotificationsByType(user.getUserId(), type, page, size),
                ResponseCode.OK
        );
    }


    @PatchMapping("/{notificationId}/read")
    public void markNotificationAsRead(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long notificationId) {
        notificationService.markAsRead(user.getUserId(), notificationId);
    }


}