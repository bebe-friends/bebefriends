package com.bbf.bebefriends.notification.service;

import com.bbf.bebefriends.member.entity.QUser;
import com.bbf.bebefriends.member.entity.QUserHotdealNotification;
import com.bbf.bebefriends.member.entity.QUserNotificationSettings;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.service.UserService;
import com.bbf.bebefriends.notification.entity.Notification;
import com.bbf.bebefriends.notification.entity.NotificationType;
import com.bbf.bebefriends.notification.repository.NotificationRepository;
import com.google.firebase.messaging.BatchResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotdealNotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final JPAQueryFactory queryFactory;
    private final FcmService fcmService;

    /**
     * 핫딜 대상자에게 알림 발송 후 기록 저장
     *
     * @param hotdealId      핫딜 게시글 ID
     * @param hotdealAges    대상 나이대
     * @param hotdealItems   대상 상품 카테고리
     * @param isNight        야간 여부
     * @param title          알림 제목
     * @param content        알림 내용
     */
    public void sendHotdealNotifications(
            Long hotdealId, List<Integer> hotdealAges, List<String> hotdealItems, boolean isNight, String title, String content
    ) {
        List<UserPushDto> targetedUsers = findHotdealTargetTokens(hotdealAges, hotdealItems, isNight);

        if (targetedUsers == null || targetedUsers.isEmpty()) {
            log.info("핫딜 알림 대상자가 없습니다.");
            return;
        }

        // FCM 발송
        List<User> successfulUsers = targetedUsers.stream()
                .map(UserPushDto::getUser)
                .collect(Collectors.toList());
        sendFcmMessages(successfulUsers, title, content);

        // 발송된 알림 기록 저장
        saveHotdealNotificationHistory(successfulUsers, hotdealId, title, content);

        log.info("핫딜 알림이 성공적으로 발송되었으며, 기록이 저장되었습니다.");
    }

    /**
     * QueryDSL - 핫딜 대상 FCM 토큰 가져오기
     */
    private List<UserPushDto> findHotdealTargetTokens(
            List<Integer> hotdealAges, List<String> hotdealItems, boolean isNight
    ) {
        QUser user = QUser.user;
        QUserHotdealNotification hotdeal = QUserHotdealNotification.userHotdealNotification;
        QUserNotificationSettings settings = QUserNotificationSettings.userNotificationSettings;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.deletedAt.isNull());
        builder.and(user.fcmToken.isNotNull());

        // 주간 야간 설정
        if (isNight) builder.and(settings.hotDealNightNotificationAgreement.isTrue());
        else builder.and(settings.hotDealNotificationAgreement.isTrue());

        // 알림 대상 나이 설정
        BooleanBuilder ageBuilder = new BooleanBuilder();
        for (Integer age : hotdealAges) {
            switch (age) {
                case 0 -> ageBuilder.or(hotdeal.age_0.isTrue());
                case 1 -> ageBuilder.or(hotdeal.age_1.isTrue());
                case 2 -> ageBuilder.or(hotdeal.age_2.isTrue());
                case 3 -> ageBuilder.or(hotdeal.age_3.isTrue());
                case 4 -> ageBuilder.or(hotdeal.age_4.isTrue());
                case 5 -> ageBuilder.or(hotdeal.age_5.isTrue());
                case 6 -> ageBuilder.or(hotdeal.age_6.isTrue());
            }
        }
        builder.and(ageBuilder);

        // 알림 대상 카테고리 설정
        if (!hotdealItems.isEmpty()) {
            BooleanBuilder categoryBuilder = new BooleanBuilder();
            for (String categoryName : hotdealItems) {
                categoryBuilder.or(hotdeal.preferredCategories.any().name.eq(categoryName));
            }
            builder.and(categoryBuilder);
        }

        return queryFactory.select(Projections.constructor(UserPushDto.class, user, user.fcmToken))
                .from(user)
                .join(user.notification, hotdeal)
                .join(user.notificationSettings, settings)
                .where(builder)
                .distinct()
                .fetch();
    }

    /**
     * FCM 메시지 발송
     *
     * @param users 알림 대상 사용자 목록
     * @param title 알림 제목
     * @param content 알림 내용
     * @return 발송 성공 사용자 목록
     */
    private List<User> sendFcmMessages(List<User> users, String title, String content) {
        List<User> successfulUsers = users.stream()
                .filter(user -> user.getFcmToken() != null && !user.getFcmToken().isBlank())
                .collect(Collectors.toList());

        final int batchSize = 1000;
        Set<String> sentTokens = new HashSet<>();

        for (int i = 0; i < successfulUsers.size(); i += batchSize) {
            List<String> batchTokens = successfulUsers.stream()
                    .skip(i)
                    .limit(batchSize)
                    .map(User::getFcmToken)
                    .filter(t -> t != null && !t.isBlank() && sentTokens.add(t))
                    .collect(Collectors.toList());

            if (batchTokens.isEmpty()) continue;

            try {
                BatchResponse response = fcmService.sendMulticastMessage(batchTokens, title, content);

                // 유효하지 않은 토큰 처리
                FcmService.FcmTokenResult failedTokens = fcmService.extractFailedTokens(response, batchTokens);
                if (!failedTokens.getRemoveTokens().isEmpty()) {
                    log.info("유효하지 않은 FCM 토큰 처리 중...");
                    // 필요한 경우 삭제 로직 수행 (ex: DB에서 제거)
                }

            } catch (Exception e) {
                log.error("FCM 알림 발송 중 오류 발생: {}", e.getMessage());
            }
        }
        return successfulUsers;
    }

    /**
     * 발송된 핫딜 알림 기록 저장
     *
     * @param users 알림 발송 성공 사용자 목록
     * @param hotdealId 핫딜 게시글 ID
     * @param title 알림 제목
     * @param content 알림 내용
     */
    private void saveHotdealNotificationHistory(List<User> users, Long hotdealId, String title, String content) {
        List<Notification> notifications = users.stream()
                .map(user -> {
                    Notification notification = Notification.createNotification(user, title, content);
                    notification.setType(NotificationType.HOTDEAL);
                    notification.setReferenceId(hotdealId);
                    notification.setRead(false);
                    return notification;
                })
                .collect(Collectors.toList());

        notificationRepository.saveAll(notifications);
        log.info("총 {}개의 핫딜 알림 기록이 저장되었습니다.", notifications.size());
    }

    @Data
    public static class UserPushDto {
        private User user;
        private String fcmToken;
        public UserPushDto(User user, String fcmToken) {
            this.user = user;
            this.fcmToken = fcmToken;
        }
    }
}
