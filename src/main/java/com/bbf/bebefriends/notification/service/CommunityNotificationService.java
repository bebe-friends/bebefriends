package com.bbf.bebefriends.notification.service;

import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserNotificationSettings;
import com.bbf.bebefriends.notification.entity.Notification;
import com.bbf.bebefriends.notification.entity.NotificationType;
import com.bbf.bebefriends.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunityNotificationService {

    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;

    /**
     * 댓글 작성 시 게시글 작성자에게 알림 발송
     *
     * @param commenter 댓글 작성자
     * @param post 댓글이 작성된 게시글
     * @param commentContent 댓글 내용
     */
    public void sendCommentNotification(User commenter, CommunityPost post, String commentContent) {
        User postCreator = post.getUser(); // 게시글 작성자

        if (postCreator == null || !isNotificationAllowed(postCreator) || !isCommentNotificationAllowed(postCreator)) {
            log.info("게시글 작성자 '{}'에게 댓글 알림을 발송하지 않습니다. (알림 동의 없음 또는 유효한 FCM 토큰 없음)",
                    postCreator != null ? postCreator.getNickname() : "NULL");
            return;
        }

        String title = "새로운 댓글 알림";
        String body = String.format("'%s'님이 '%s' 게시글에 댓글을 달았습니다: %s",
                commenter.getNickname(), post.getTitle(), commentContent);

        fcmService.sendPushNotification(postCreator.getFcmToken(), title, body);
        log.info("게시글 작성자 '{}'에게 댓글 알림을 발송하였습니다.", postCreator.getNickname());

        saveNotification(postCreator, title, body, NotificationType.COMMENT);
    }

    /**
     * 답글 작성 시 상위 댓글 작성자에게 알림 발송
     *
     * @param replier 답글 작성자
     * @param parentComment 답글이 작성된 상위 댓글
     * @param replyContent 답글 내용
     */
    public void sendReplyNotification(User replier, CommunityComment parentComment, String replyContent) {
        User commentCreator = parentComment.getUser(); // 상위 댓글 작성자

        if (commentCreator == null || !isNotificationAllowed(commentCreator) || !isCommentNotificationAllowed(commentCreator)) {
            log.info("댓글 작성자 '{}'에게 답글 알림을 발송하지 않습니다. (알림 동의 없음 또는 유효한 FCM 토큰 없음)",
                    commentCreator != null ? commentCreator.getNickname() : "NULL");
            return;
        }

        String title = "새로운 답글 알림";
        String body = String.format("'%s'님이 당신의 댓글에 답글을 달았습니다: %s",
                replier.getNickname(), replyContent);

        fcmService.sendPushNotification(commentCreator.getFcmToken(), title, body);
        log.info("댓글 작성자 '{}'에게 답글 알림을 발송하였습니다.", commentCreator.getNickname());

        saveNotification(commentCreator, title, body, NotificationType.COMMENT);
    }

    /**
     * 사용자 알림 동의 상태 확인
     *
     * @param user 알림을 받을 사용자
     * @return 알림 전송 가능 여부
     */
    private boolean isNotificationAllowed(User user) {
        if (user.getFcmToken() == null || user.getFcmToken().isBlank()) {
            log.info("사용자 '{}'는 유효한 FCM 토큰이 없어 알림을 받을 수 없습니다.", user.getNickname());
            return false;
        }
        return true;
    }

    /**
     * 댓글 알림 동의 여부 확인
     *
     * @param user 알림을 받을 사용자
     * @return 댓글 알림 동의 여부
     */
    private boolean isCommentNotificationAllowed(User user) {
        UserNotificationSettings settings = user.getNotificationSettings();
        if (settings == null || !settings.isCommentNotificationAgreement()) {
            log.info("사용자 '{}'는 댓글 알림 수신에 동의하지 않았습니다.", user.getNickname());
            return false;
        }
        return true;
    }

    private void saveNotification(User user, String title, String content, NotificationType type) {
        Notification notification = Notification.createNotification(user, title, content);
        notification.setType(type); // 알림 타입 설정
        notification.setRead(false); // 초기값은 읽지 않음 상태
        notificationRepository.save(notification);
        log.info("알림이 DB에 저장되었습니다. 사용자: '{}', 제목: '{}'", user.getNickname(), title);
    }

}
