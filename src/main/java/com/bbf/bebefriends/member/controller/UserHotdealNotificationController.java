package com.bbf.bebefriends.member.controller;


import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.service.UserHotdealNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotdeal-notification")
@RequiredArgsConstructor
@Tag(name = "유저 핫딜 알림 설정", description = "사용자 관심 핫딜 알림 설정 API")
public class UserHotdealNotificationController {

    private final UserHotdealNotificationService hotdealNotificationService;

    @GetMapping
    @Operation(summary = "핫딜 알림 설정 조회", description = "특정 사용자의 핫딜 알림 설정 정보를 조회합니다.")
    public BaseResponse<UserDTO.UserHotdealNotificationResponse> getHotdealNotificationSettings(
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        return BaseResponse.onSuccess(hotdealNotificationService.getHotdealNotificationSettings(user.getUser()), ResponseCode.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "핫딜 알림 설정 업데이트", description = "사용자의 핫딜 알림 설정 정보를 업데이트합니다.")
    public void updateHotdealNotificationSettings(
            @RequestBody UserDTO.UserHotdealNotificationRequest request,
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        hotdealNotificationService.updateHotdealNotificationSettings(request, user.getUser());
    }

}
