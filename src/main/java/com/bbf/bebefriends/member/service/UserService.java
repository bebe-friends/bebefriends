package com.bbf.bebefriends.member.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.AuthDTO;
import com.bbf.bebefriends.member.dto.JwtPayload;
import com.bbf.bebefriends.member.dto.KakaoUserInfo;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.entity.*;
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

    public UserDTO.UserInfoResponse getUserInfo(Long uid) {
        User user = findByUid(uid);
        return new UserDTO.UserInfoResponse(user.getNickname());
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

        if (userRepository.existsByNickname(nickname)) {
            throw new UserControllerAdvice(ResponseCode.NICKNAME_ALREADY_EXIST);
        }
    }

    private String createNickname() {
        String nickname;
        do {
            nickname = NicknameGeneratorUtil.generateNickname();
        } while (userRepository.existsByNickname(nickname));
        return nickname;
    }

    @Transactional
    public UserDTO.UserAccessResponse loginUser(UserDTO.UserLoginRequest request) {
        KakaoUserInfo userInfo = kakaoOAuthService.getUserInfo(request.oauthToken());
        User user = userRepository.findByDeletedAtIsNullAndOauth2UserInfo_OauthId(userInfo.getId())
                .orElseGet(() -> {
                            User findUser = userRepository.findByEmailAndDeletedAtIsNull(userInfo.getEmail())
                                    .orElseThrow(() -> new UserControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));

                            // Oauth2UserInfo가 없는 경우 새로 생성
                            if (findUser.getOauth2UserInfo() == null) {
                                Oauth2UserInfo oauth2UserInfo = new Oauth2UserInfo();
                                oauth2UserInfo.setOauthId(userInfo.getId());
                                findUser.setOauth2UserInfo(oauth2UserInfo);
                            } else {
                                findUser.getOauth2UserInfo().setOauthId(userInfo.getId());
                            }
                            return findUser;
                        }
                );
        
        return new UserDTO.UserAccessResponse(
                JwtTokenUtil.createAccessToken(String.valueOf(user.getUid()),
                        new JwtPayload(UserRole.USER.toString(), user.getEmail()))
        );
    }

    @Transactional
    public UserDTO.UserAccessResponse registerUser(UserDTO.UserSignupRequest request) {
        KakaoUserInfo userInfo = kakaoOAuthService.getUserInfo(request.oauthToken());
        User user = userRepository.findByOauth2UserInfo_OauthId(userInfo.getId())
                .orElse(null);

        if (user != null) {
            if (user.getDeletedAt() != null) {
                user.setNickname(createNickname());
                user.setDeletedAt(null);
                return new UserDTO.UserAccessResponse(
                        JwtTokenUtil.createAccessToken(String.valueOf(user.getUid()),
                                new JwtPayload(UserRole.USER.toString(), user.getEmail()))
                );
            } else {
                throw new UserControllerAdvice(ResponseCode.MEMBER_ALREADY_EXIST);
            }
        }

        user = new User();
        user.setNickname(createNickname());
        user.setEmail(userInfo.getEmail());
        user.setRole(UserRole.USER);
        user.setPhone(userInfo.getPhone());
        user.setFcmToken(request.fcmToken());

        Oauth2UserInfo oauth2UserInfo = Oauth2UserInfo.of(user, userInfo.getId());
        user.setOauth2UserInfo(oauth2UserInfo);

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

        UserHotdealNotification userHotdealNotification = UserHotdealNotification.of(user);
        user.setNotification(userHotdealNotification);

        User createdUser = userRepository.save(user);
        String accessToken = JwtTokenUtil.createAccessToken(String.valueOf(createdUser.getUid()),
                            new JwtPayload(UserRole.USER.toString(), user.getEmail()));
        return new UserDTO.UserAccessResponse(accessToken);
    }

    @Transactional
    public void deleteUser(Long uid) {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));

        user.setNickname(createNickname());
        user.setDeletedAt(LocalDateTime.now());
    }

    public void updateFcmToken(Long uid, AuthDTO.FcmTokenUpdateRequest req) {
        User user = findByUid(uid);

        if (req.newFcmToken() != null && !req.newFcmToken().isBlank()) {
            user.setFcmToken(req.newFcmToken());
        }

        userRepository.save(user);
    }

}
