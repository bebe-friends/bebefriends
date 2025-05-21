package com.bbf.bebefriends.notification.controller;


import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.notification.dto.FcmDTO;
import com.bbf.bebefriends.notification.service.FcmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "푸시 알림", description = "FCM 푸시 알림 테스트")
@RestController
@RequestMapping("/api/v1/push")
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @Operation(summary = "FCM 푸시 알림", description = "fcm token 사용해서 푸시 알림 테스트")
    @PostMapping("/send")
    public BaseResponse<String> sendNotification(
            @Valid @RequestBody FcmDTO.FcmTestRequest request
    ) {
        
        fcmService.sendPushNotification(request.token(), request.title(), request.body());
        return BaseResponse.onSuccess("푸시 알림 전송 완료.", ResponseCode.OK);
    }
}