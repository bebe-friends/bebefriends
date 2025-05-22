package com.bbf.bebefriends.member.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.TokenDTO;
import com.bbf.bebefriends.member.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원", description = "닉네임 수정 및 FCM 토큰 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "닉네임 갱신", description = "닉네임 중복 체크 후 갱신")
    @PutMapping("/update-nickname")
    public BaseResponse<String> updateNickname(@RequestParam String nickname,
                                               @AuthenticationPrincipal UserDetailsImpl user
    ) {
        userService.validateNicknameAvailability(nickname);
        userService.updateNickname(user.getUserId(), nickname);
        return BaseResponse.onSuccess("닉네임이 변경되었습니다.", ResponseCode.OK);
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 기능")
    @DeleteMapping("/delete")
    public BaseResponse<String> deleteUser(@AuthenticationPrincipal UserDetailsImpl user) {
        userService.deleteUser(user.getUserId());
        return BaseResponse.onSuccess("탈퇴 성공", ResponseCode.NO_CONTENT);
    }

    @SecurityRequirement(name = "firebaseAuth")
    @Operation(summary = "FCM 토큰 갱신", description = "Firebase ID 인증 이후 클라이언트의 새로운 FCM 토큰으로 갱신")
    @PutMapping("/fcm-token")
    public BaseResponse<?> updateFcmToken(
            @Valid @RequestBody TokenDTO.FcmTokenUpdateRequest request,
            Authentication authentication
    ) {
        Long uid = (Long) authentication.getPrincipal();
        userService.updateFcmToken(uid, request);
        return BaseResponse.onSuccess(uid, ResponseCode.OK);
    }
}
