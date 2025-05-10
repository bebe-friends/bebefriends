package com.bbf.bebefriends.member.repository;

import com.bbf.bebefriends.member.entity.RefreshToken;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUid(String uid);

    void deleteByUid(String uid);

    Optional<RefreshToken> findRefreshTokenByUser(User user);
}