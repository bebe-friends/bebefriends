package com.bbf.bebefriends.member.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.JwtPayload;
import com.bbf.bebefriends.member.entity.RefreshToken;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserRole;
import com.bbf.bebefriends.member.exception.UserControllerAdvice;
import com.bbf.bebefriends.member.repository.RefreshTokenRepository;
import com.bbf.bebefriends.member.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.bbf.bebefriends.member.util.JwtTokenUtil.REFRESH_TOKEN_EXPIRATION;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    /**
     * 리프레시 토큰 재 발급
     *
     * @param uid 사용자 고유 ID
     * @return access token
     */
    public String generateRefreshToken(String uid) {
        User user = userService.findByUid(uid);
        RefreshToken refreshTokenEntity = validateRefreshToken(user.getRefreshToken().getToken());

        String accessToken = JwtTokenUtil.createAccessToken(uid, new JwtPayload(UserRole.USER.toString(), user.getEmail()));
        String refreshToken = JwtTokenUtil.createRefreshToken(uid);

        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setCreatedAt(LocalDateTime.now());
        refreshTokenEntity.setExpiresAt(LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRATION));

        refreshTokenRepository.save(refreshTokenEntity);
        return accessToken;
    }

    /**
     * 토큰 유효성 확인
     *
     * @param token 확인할 리프레시 토큰
     * @return 유효한 경우 RefreshToken 엔티티 반환
     */
    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.TOKEN_INVALID_EXCEPTION));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new UserControllerAdvice(ResponseCode.TOKEN_EXPIRED_EXCEPTION);
        }

        return refreshToken;
    }

}