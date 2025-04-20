package com.bbf.bebefriends.member.service;

import com.bbf.bebefriends.member.dto.FcmTokenUpdateRequest;
import com.bbf.bebefriends.member.dto.UserSignupRequest;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserNotificationSettings;
import com.bbf.bebefriends.member.entity.UserRole;
import com.bbf.bebefriends.member.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(String uid, UserSignupRequest request) {
        if (userRepository.existsByNicknameAndDeletedAtIsNull(request.nickname())) {
            throw new IllegalArgumentException("해당 닉네임은 이미 사용 중입니다.");
        }

        User user = new User();
        user.setUid(uid);
        user.setNickname(request.nickname());
        user.setEmail(getEmailFromFirebase(uid)); // optional
        user.setRole(UserRole.USER);
        user.setFcmToken(request.fcmToken());

        UserNotificationSettings settings = UserNotificationSettings.of(
                user,
                request.hotDealNotification(),
                request.commentNotification(),
                request.nightDealNotification(),
                request.marketingNotification()
        );
        user.setNotificationSettings(settings);

        userRepository.save(user);
    }

    private String getEmailFromFirebase(String uid) {
        try {
            UserRecord record = FirebaseAuth.getInstance().getUser(uid);
            return record.getEmail();
        } catch (FirebaseAuthException e) {
            return null;
        }
    }

    public void deleteUser(String uid) {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void updateFcmToken(String uid, FcmTokenUpdateRequest req) {
        User user = userRepository.findByUidAndDeletedAtIsNull(uid)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        if (req.newFcmToken() != null && !req.newFcmToken().isBlank()) {
            user.setFcmToken(req.newFcmToken());
        }

        userRepository.save(user);
    }

}
