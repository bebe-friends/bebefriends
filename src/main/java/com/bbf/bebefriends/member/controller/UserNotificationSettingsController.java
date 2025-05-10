package com.bbf.bebefriends.member.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.service.UserNotificationSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification-settings")
@RequiredArgsConstructor
@Tag(name = "User Notification Settings", description = "사용자 알림 설정 API")
public class UserNotificationSettingsController {

    private final UserNotificationSettingsService notificationSettingsService;

    @PutMapping("/update")
    @Operation(summary = "알림 설정 업데이트", description = "사용자의 알림 설정 정보를 업데이트합니다.")
    public void updateNotificationSettings(
            @RequestBody UserDTO.UpdateNotificationSettingsRequest request
    ) {
        notificationSettingsService.updateNotificationSettings(request);
    }

    @GetMapping("/{uid}")
    @Operation(summary = "알림 설정 조회", description = "특정 사용자의 알림 설정 정보를 조회합니다.")
    public BaseResponse<UserDTO.UpdateNotificationSettingsResponse> getNotificationSettings(
            @PathVariable String uid
    ) {
        return BaseResponse.onSuccess(notificationSettingsService.getNotificationSettings(uid), ResponseCode.OK);
    }
}