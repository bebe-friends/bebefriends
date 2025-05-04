package com.bbf.bebefriends.member.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.service.UserService;
import com.bbf.bebefriends.member.util.FirebaseAuthUtil;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원(User)", description = "회원가입 및 FCM 토큰 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "Firebase ID 토큰 및 알림 설정을 포함한 사용자 가입")
    @PostMapping("/signup")
    public BaseResponse<?> signup(@Valid @RequestBody UserDTO.UserSignupRequest request) {
        userService.validateNicknameAvailability(request.nickname());

        try {
            String uid = FirebaseAuthUtil.getUidFromToken(request.idToken());
            userService.registerUser(uid, request);
            return BaseResponse.onSuccess("가입 성공", ResponseCode.CREATED);
        } catch (FirebaseAuthException e) {
            return BaseResponse.onFailure(null, ResponseCode.FIREBASE_TOKEN_INVALID_EXCEPTION);
        } catch (Exception e) {
            return BaseResponse.onFailure("An error occurred while registering user.", ResponseCode._INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 사용 가능 여부 확인")
    @GetMapping("/check-nickname")
    public BaseResponse<?> checkNickname(@RequestParam String nickname) {
        userService.validateNicknameAvailability(nickname);
        return BaseResponse.onSuccess("사용가능한 닉네임 입니다.", ResponseCode.OK);
    }

    @Operation(summary = "로그인", description = "Firebase ID 토큰으로 로그인")
    @PostMapping("/login")
    public BaseResponse<UserDTO.UserLoginResponse> login(@RequestParam String idToken) {
        String uid;
        try {
            uid = FirebaseAuthUtil.getUidFromToken(idToken);
        } catch (FirebaseAuthException e) {
            return BaseResponse.onFailure(null, ResponseCode.FIREBASE_TOKEN_INVALID_EXCEPTION);
        }
        return BaseResponse.onSuccess(userService.loginUser(uid), ResponseCode.OK);
    }
}
