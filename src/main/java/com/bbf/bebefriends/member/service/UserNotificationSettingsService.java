package com.bbf.bebefriends.member.service;

import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserNotificationSettings;
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
    public void updateNotificationSettings(String uid,
                                           boolean hotDealNotification,
                                           boolean commentNotification,
                                           boolean nightDealNotification,
                                           boolean marketingNotification
    ) {

        User user = userRepository.findByUidAndDeletedAtIsNull(uid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        UserNotificationSettings settings = notificationSettingsRepository.findById(uid)
                .orElseGet(() -> {
                    UserNotificationSettings s = new UserNotificationSettings();
                    s.setUid(uid);
                    s.setUser(user); // 양방향 설정
                    return s;
                });

        settings.setHotDealNotificationAgreement(hotDealNotification);
        settings.setCommentNotificationAgreement(commentNotification);
        settings.setHotDealNightNotificationAgreement(nightDealNotification);
        settings.setMarketingNotificationAgreement(marketingNotification);

        notificationSettingsRepository.save(settings);
    }

    public UserNotificationSettings getNotificationSettings(String uid) {
        return notificationSettingsRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("알림 설정이 존재하지 않습니다."));
    }
}