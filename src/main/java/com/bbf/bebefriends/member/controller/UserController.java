package com.bbf.bebefriends.member.controller;

import com.bbf.bebefriends.member.dto.FcmTokenUpdateRequest;
import com.bbf.bebefriends.member.dto.NicknameValidator;
import com.bbf.bebefriends.member.dto.UserSignupRequest;
import com.bbf.bebefriends.member.service.FirebaseAuthService;
import com.bbf.bebefriends.member.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원(User)", description = "회원가입 및 FCM 토큰 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final FirebaseAuthService firebaseAuthService;
    private final UserService userService;

    @Operation(summary = "회원가입", description = "Firebase ID 토큰 및 알림 설정을 포함한 사용자 가입")
    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupRequest request) {
        if (!NicknameValidator.isValid(request.nickname())) {
            return ResponseEntity.badRequest().body("Invalid nickname format.");
        }

        try {
            String uid = firebaseAuthService.getUidFromToken(request.idToken());
            userService.registerUser(uid, request);
            return ResponseEntity.ok().build();
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Firebase token.");
        }
    }

    @SecurityRequirement(name = "firebaseAuth")
    @Operation(summary = "FCM 토큰 갱신", description = "Firebase ID 인증 이후 클라이언트의 새로운 FCM 토큰으로 갱신")
    @PostMapping("/fcm-token")
    public ResponseEntity<?> updateFcmToken(
            @Valid @RequestBody FcmTokenUpdateRequest request,
            Authentication authentication
    ) {
        String uid = (String) authentication.getPrincipal();
        userService.updateFcmToken(uid, request);
        return ResponseEntity.ok().build();
    }
}
