package com.bbf.bebefriends.member.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.JwtPayload;
import com.bbf.bebefriends.member.dto.TokenDTO;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.entity.*;
import com.bbf.bebefriends.member.exception.UserControllerAdvice;
import com.bbf.bebefriends.member.repository.UserRepository;
import com.bbf.bebefriends.member.util.JwtTokenUtil;
import com.bbf.bebefriends.member.util.NicknameValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.bbf.bebefriends.member.util.JwtTokenUtil.REFRESH_TOKEN_EXPIRATION;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUid(String uid) {
        return userRepository.findByUidAndDeletedAtIsNull(uid)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));
    }

    public void validateNicknameAvailability(String nickname){
        if (!NicknameValidator.isValid(nickname)) {
            throw new UserControllerAdvice(ResponseCode.NICKNAME_INVALID);
        }

        if (userRepository.existsByNicknameAndDeletedAtIsNull(nickname)) {
            throw new UserControllerAdvice(ResponseCode.NICKNAME_ALREADY_EXIST);
        }
    }

    @Transactional
    public UserDTO.UserLoginResponse loginUser(String uid) {
        User user = findByUid(uid);
        String accessToken = JwtTokenUtil.createAccessToken(user.getUid(), new JwtPayload(UserRole.USER.toString(), user.getEmail()));
        return new UserDTO.UserLoginResponse(accessToken);
    }

    @Transactional
    public void registerUser(String uid, UserDTO.UserSignupRequest request) {
        validateNicknameAvailability(request.nickname());

        User user = new User();
        user.setUid(uid);
        user.setNickname(request.nickname());
        user.setEmail(getEmailFromFirebase(uid)); // optional
        user.setRole(UserRole.USER);
        user.setFcmToken(request.fcmToken());

        UserNotificationSettings settings = UserNotificationSettings.of(
                user,
                request.hotDealNotification(),
                request.nightHotDealNotification(),
                request.marketingNotification(),
                request.nightMarketingNotification(),
                request.commentNotification()
                );
        user.setNotificationSettings(settings);

        UserTermsAgreements termsAgreements = UserTermsAgreements.of(
                user, request.agreement(), request.privatePolicy(), request.age()
        );
        user.setTermsAgreements(termsAgreements);

        String refreshToken = JwtTokenUtil.createRefreshToken(uid);
        RefreshToken refreshTokenEntity = RefreshToken.createToken(
                user,
                refreshToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRATION)
        );
        user.setRefreshToken(refreshTokenEntity);

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
        User user = findByUid(uid);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void updateFcmToken(String uid, TokenDTO.FcmTokenUpdateRequest req) {
        User user = findByUid(uid);

        if (req.newFcmToken() != null && !req.newFcmToken().isBlank()) {
            user.setFcmToken(req.newFcmToken());
        }

        userRepository.save(user);
    }

}
