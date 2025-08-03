package com.bbf.bebefriends.member.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserNotificationSettings;
import com.bbf.bebefriends.member.exception.UserControllerAdvice;
import com.bbf.bebefriends.member.repository.UserNotificationSettingsRepository;
import com.bbf.bebefriends.member.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationSettingsService {

    private final UserNotificationSettingsRepository notificationSettingsRepository;
    private final UserRepository userRepository;

    @Transactional
    public void updateNotificationSettings(UserDTO.UpdateNotificationSettingsRequest request, Long uid) {

        User user = userRepository.findByUidAndDeletedAtIsNull(uid)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));

        UserNotificationSettings settings = notificationSettingsRepository.findUserNotificationSettingsByUser(user)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode._INTERNAL_SERVER_ERROR));

        settings.setHotDealNotificationAgreement(request.hotDealNotification());
        settings.setHotDealNightNotificationAgreement(request.hotDealNightNotification());
        settings.setMarketingNotificationAgreement(request.marketingNotification());
        settings.setMarketingNightNotificationAgreement(request.marketingNightNotification());
        settings.setCommentNotificationAgreement(request.commentNotification());

        notificationSettingsRepository.save(settings);
    }

    public UserDTO.UpdateNotificationSettingsResponse getNotificationSettings(Long uid) {
        UserNotificationSettings settings = notificationSettingsRepository.findUserNotificationSettingsByUid(uid)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode._INTERNAL_SERVER_ERROR));

        return new UserDTO.UpdateNotificationSettingsResponse(
                settings.isHotDealNotificationAgreement(),
                settings.isHotDealNightNotificationAgreement(),
                settings.isMarketingNotificationAgreement(),
                settings.isMarketingNightNotificationAgreement(),
                settings.isCommentNotificationAgreement()
        );
    }
}