package com.bbf.bebefriends.notification.controller;


import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.notification.dto.FcmDTO;
import com.bbf.bebefriends.notification.service.FcmService;
import com.bbf.bebefriends.notification.service.HotdealNotificationService;
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
    private final HotdealNotificationService hotdealNotificationService;

    @Operation(summary = "FCM 푸시 알림", description = "fcm token 사용해서 푸시 알림 테스트")
    @PostMapping("/send")
    public BaseResponse<String> sendNotification(
            @Valid @RequestBody FcmDTO.FcmTestRequest request
    ) {
        fcmService.sendPushNotification(request.token(), request.title(), request.body());
        return BaseResponse.onSuccess("푸시 알림 전송 완료.", ResponseCode.OK);
    }

    @Operation(summary = "핫딜 게시글 알림 전송", description = "핫딜 게시글에 대해 설정된 나이, 카테고리 대상 사용자들에게 푸시 알림 전송")
    @PostMapping("/hotdeal")
    public BaseResponse<String> sendHotdealNotification(
            @Valid @RequestBody FcmDTO.HotdealNotificationRequest request
    ) {
        hotdealNotificationService.sendHotdealNotifications(
                request.hotdealId(),
                request.ages(),
                request.categories(),
                request.isNight(),
                request.title(),
                request.body()
        );
        return BaseResponse.onSuccess("핫딜 푸시 알림 전송 완료.", ResponseCode.OK);
    }

}