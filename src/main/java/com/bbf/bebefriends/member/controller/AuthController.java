package com.bbf.bebefriends.member.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "회원가입 및 로그인 API @조정우")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "Kakao oauth 토큰 및 알림 설정을 포함한 사용자 가입")
    @PostMapping("/signup")
    public BaseResponse<UserDTO.UserAccessResponse> signup(@Valid @RequestBody UserDTO.UserSignupRequest request) {
        try {
            return BaseResponse.onSuccess(userService.registerUser(request), ResponseCode.CREATED);
        } catch (Exception e) {
            return BaseResponse.onFailure(null, ResponseCode._INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "로그인", description = "Kakao oauth 토큰으로 로그인")
    @PostMapping("/login")
    public BaseResponse<UserDTO.UserAccessResponse> login(@Valid @RequestBody UserDTO.UserLoginRequest request) {
        return BaseResponse.onSuccess(userService.loginUser(request), ResponseCode.OK);
    }
}