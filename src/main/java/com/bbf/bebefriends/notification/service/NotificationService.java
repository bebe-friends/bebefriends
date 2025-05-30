package com.bbf.bebefriends.notification.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.exception.UserControllerAdvice;
import com.bbf.bebefriends.member.service.UserService;
import com.bbf.bebefriends.notification.entity.Notification;
import com.bbf.bebefriends.notification.entity.NotificationType;
import com.bbf.bebefriends.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    /**
     * 특정 유저의 알림 목록 조회 (페이징)
     *
     * @param userId 유저 ID
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @return 알림 목록 (페이징 결과)
     */
    @Transactional(readOnly = true)
    public Page<Notification> getNotificationsByUser(Long userId, int page, int size) {
        User user = userService.findByUid(userId);
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
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
    public Page<Notification> getNotificationsByType(Long userId, NotificationType type, int page, int size) {
        User user = userService.findByUid(userId);
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.findByUserAndTypeOrderByCreatedAtDesc(user, type, pageable);
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

}