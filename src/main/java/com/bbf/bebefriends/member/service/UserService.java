package com.bbf.bebefriends.member.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.JwtPayload;
import com.bbf.bebefriends.member.dto.KakaoUserInfo;
import com.bbf.bebefriends.member.dto.TokenDTO;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserNotificationSettings;
import com.bbf.bebefriends.member.entity.UserRole;
import com.bbf.bebefriends.member.entity.UserTermsAgreements;
import com.bbf.bebefriends.member.exception.UserControllerAdvice;
import com.bbf.bebefriends.member.repository.UserRepository;
import com.bbf.bebefriends.member.util.JwtTokenUtil;
import com.bbf.bebefriends.member.util.NicknameGeneratorUtil;
import com.bbf.bebefriends.member.util.NicknameValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KakaoOAuthService kakaoOAuthService;

    public User findByUid(Long uid) {
        return userRepository.findByUidAndDeletedAtIsNull(uid)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));
    }

    public void updateNickname(Long uid, String nickname) {
        User user = findByUid(uid);
        user.setNickname(nickname);
        userRepository.save(user);
    }

    public void validateNicknameAvailability(String nickname){
        if (!NicknameValidator.isValid(nickname)) {
            throw new UserControllerAdvice(ResponseCode.NICKNAME_INVALID);
        }

        if (userRepository.existsByNicknameAndDeletedAtIsNull(nickname)) {
            throw new UserControllerAdvice(ResponseCode.NICKNAME_ALREADY_EXIST);
        }
    }

    private String createNickname() {
        String nickname;
        do {
            nickname = NicknameGeneratorUtil.generateNickname();
        } while (userRepository.existsByNicknameAndDeletedAtIsNull(nickname));
        return nickname;
    }

    @Transactional
    public UserDTO.UserAccessResponse loginUser(UserDTO.UserLoginRequest request) {
        KakaoUserInfo userInfo = kakaoOAuthService.getUserInfo(request.oauthToken());
        User user = findByUid(userInfo.getId());

        return new UserDTO.UserAccessResponse(
                JwtTokenUtil.createAccessToken(String.valueOf(user.getUid()),
                        new JwtPayload(UserRole.USER.toString(), user.getEmail()))
        );
    }

    @Transactional
    public UserDTO.UserAccessResponse registerUser(UserDTO.UserSignupRequest request) {
        KakaoUserInfo userInfo = kakaoOAuthService.getUserInfo(request.oauthToken());

        User user = new User();
        user.setNickname(createNickname());
        user.setEmail(userInfo.getEmail()); // optional
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

        User createdUser = userRepository.save(user);
        String accessToken = JwtTokenUtil.createAccessToken(String.valueOf(createdUser.getUid()),
                            new JwtPayload(UserRole.USER.toString(), user.getEmail()));
        return new UserDTO.UserAccessResponse(accessToken);
    }

    public void deleteUser(Long uid) {
        User user = findByUid(uid);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void updateFcmToken(Long uid, TokenDTO.FcmTokenUpdateRequest req) {
        User user = findByUid(uid);

        if (req.newFcmToken() != null && !req.newFcmToken().isBlank()) {
            user.setFcmToken(req.newFcmToken());
        }

        userRepository.save(user);
    }

}
