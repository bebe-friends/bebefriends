package com.bbf.bebefriends.member.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.TokenDTO;
import com.bbf.bebefriends.member.service.RefreshTokenService;
import com.bbf.bebefriends.member.service.UserService;
import com.bbf.bebefriends.member.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @SecurityRequirement(name = "firebaseAuth")
    @Operation(summary = "FCM 토큰 갱신", description = "Firebase ID 인증 이후 클라이언트의 새로운 FCM 토큰으로 갱신")
    @PostMapping("/fcm-token")
    public BaseResponse<?> updateFcmToken(
            @Valid @RequestBody TokenDTO.FcmTokenUpdateRequest request,
            Authentication authentication
    ) {
        String uid = (String) authentication.getPrincipal();
        userService.updateFcmToken(uid, request);
        return BaseResponse.onSuccess(uid, ResponseCode.OK);
    }

    @PostMapping("/validate")
    public BaseResponse<String> validateToken(@RequestParam String token) {
        if (JwtTokenUtil.validateAccessToken(token)) {
            return BaseResponse.onSuccess(token, ResponseCode.OK);
        } else {
            return BaseResponse.onFailure(null, ResponseCode.TOKEN_INVALID_EXCEPTION);
        }
    }

    @PostMapping("/generate-token")
    @Operation(summary = "재 토큰 발급", description = "사용자 고유 ID로 리프레시 토큰을 갱신하고 액세스 토큰을 발급합니다.")
    public BaseResponse<String> generateToken(@RequestParam String uid) {
        return BaseResponse.onSuccess(refreshTokenService.generateRefreshToken(uid), ResponseCode.OK);
    }
}