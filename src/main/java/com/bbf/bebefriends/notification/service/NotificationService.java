package com.bbf.bebefriends.notification.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.QUser;
import com.bbf.bebefriends.member.entity.QUserHotdealNotification;
import com.bbf.bebefriends.member.entity.QUserNotificationSettings;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.exception.UserControllerAdvice;
import com.bbf.bebefriends.member.service.UserService;
import com.bbf.bebefriends.notification.dto.NotificationDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final JPAQueryFactory queryFactory;
    private final FcmService fcmService;

    /**
     * 특정 유저의 알림 목록 조회 (페이징)
     *
     * @param userId 유저 ID
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @return 알림 목록 (페이징 결과)
     */
    @Transactional(readOnly = true)
    public Page<NotificationDTO.NotificationResponse> getNotificationsByUser(Long userId, int page, int size) {
        User user = userService.findByUid(userId);
        Pageable pageable = PageRequest.of(page, size);

        Page<Notification> notifications = notificationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        return notifications.map(this::toDto);
    }

    /**
     * 특정 유저의 특정 타입 알림 조회 (페이징)
     *
     * @param userId 유저 ID
     * @param type 알림 타입
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 알림 목록 (페이징 결과)
     */
    @Transactional(readOnly = true)
    public Page<NotificationDTO.NotificationResponse> getNotificationsByType(Long userId, NotificationType type, int page, int size) {
        User user = userService.findByUid(userId);
        Pageable pageable = PageRequest.of(page, size);

        Page<Notification> notifications = notificationRepository.findByUserAndTypeOrderByCreatedAtDesc(user, type, pageable);
        return notifications.map(this::toDto);
    }

    /**
     * 알림 읽음 처리
     *
     * @param userId 유저 ID
     * @param notificationId 알림 ID
     */
    @Transactional
    public void markAsRead(Long userId, Long notificationId) {
        User user = userService.findByUid(userId);

        Notification notification = notificationRepository.findByIdAndUser(notificationId, user);
        if (notification == null) {
            log.error("해당 알림을 찾을 수 없습니다. ID: {}", notificationId);
            throw new UserControllerAdvice(ResponseCode._BAD_REQUEST);
        }

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    /**
     * 핫딜 대상자에게 알림 발송
     * ++ 핫딜 ID 값을 넘겨줄지 어떻게 핫딜 게시글 정보를 사용자에게 알림으로 넘길지 논의 필요
     *
     * @param hotdealId      핫딜 게시글 ID (미확정..)
     * @param hotdealAges    대상 나이대
     * @param hotdealItems   대상 상품 카테고리
     * @param isNight        야간 여부
     * @param title          알림 제목
     * @param content        알림 내용
     */
    public void sendHotdealNotifications(
            Long hotdealId, List<Integer> hotdealAges, List<String> hotdealItems, boolean isNight, String title, String content
    ) {
        List<UserPushDto> userTokens = findHotdealTargetTokens(hotdealAges, hotdealItems, isNight);

        if (userTokens == null || userTokens.isEmpty()) {
            log.info("핫딜 알림 대상자가 없습니다.");
            return;
        }

        fcmSend(userTokens, title, content);
        log.info("핫딜 알림이 성공적으로 발송되었습니다.");
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

        return queryFactory.select(Projections.constructor(UserPushDto.class, user.fcmToken))
                .from(user)
                .join(user.notification, hotdeal)
                .join(user.notificationSettings, settings)
                .where(builder)
                .distinct()
                .fetch();
    }

    /**
     * Firebase FCM 알림 발송 로직
     */
    private void fcmSend(List<UserPushDto> tokens, String title, String content) {
        final int batchSize = 1000;
        Set<String> sentTokens = new HashSet<>();

        for (int i = 0; i < tokens.size(); i += batchSize) {
            List<String> batchTokens = tokens.stream()
                    .skip(i)
                    .limit(batchSize)
                    .map(UserPushDto::getFcmToken)
                    .filter(t -> t != null && !t.isBlank() && sentTokens.add(t))
                    .collect(Collectors.toList());

            if (batchTokens.isEmpty()) continue;

            try {
                BatchResponse response = fcmService.sendMulticastMessage(batchTokens, title, content);

                // 실패/삭제/재시도 대상 처리
                FcmService.FcmTokenResult failedTokens = fcmService.extractFailedTokens(response, batchTokens);
                if (!failedTokens.getRemoveTokens().isEmpty()) {
                    log.info("유효하지 않은 FCM 토큰 처리 중...");
                    // 필요한 경우 삭제 로직 수행 (ex: DB에서 제거)
                }

            } catch (Exception e) {
                log.error("FCM 알림 발송 중 오류 발생: {}", e.getMessage());
            }

        }
    }


    /**
     * 엔티티를 DTO로 변환 (Notification -> NotificationResponse)
     *
     * @param notification 알림 엔티티
     * @return NotificationResponse DTO
     */
    private NotificationDTO.NotificationResponse toDto(Notification notification) {
        return new NotificationDTO.NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getContent(),
                notification.getType(),
                notification.getCreatedAt(),
                notification.isRead()
        );
    }

    @Data
    public static class UserPushDto {
        private String fcmToken;
        public UserPushDto(String fcmToken) { this.fcmToken = fcmToken; }
    }

}