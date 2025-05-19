package com.bbf.bebefriends.member.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.service.UserNotificationSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification-settings")
@RequiredArgsConstructor
@Tag(name = "유저 알림 설정", description = "사용자 알림 설정 API")
public class UserNotificationSettingsController {

    private final UserNotificationSettingsService notificationSettingsService;

    @GetMapping
    @Operation(summary = "알림 설정 조회", description = "특정 사용자의 알림 설정 정보를 조회합니다.")
    public BaseResponse<UserDTO.UpdateNotificationSettingsResponse> getNotificationSettings(
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        return BaseResponse.onSuccess(notificationSettingsService.getNotificationSettings(user.getUser().getUid()), ResponseCode.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "알림 설정 업데이트", description = "사용자의 알림 설정 정보를 업데이트합니다.")
    public void updateNotificationSettings(
            @RequestBody UserDTO.UpdateNotificationSettingsRequest request,
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        notificationSettingsService.updateNotificationSettings(request, user.getUserId());
    }

}