package com.bbf.bebefriends.member.repository;


import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // soft setDeletedAt 된 유저 제외
    Optional<User> findByUidAndDeletedAtIsNull(Long uid);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.nickname = :nickname, u.deletedAt = :deletedAt " +
            "WHERE u.uid = :uid")
    void updateNicknameAndDeletedAtByUid(@Param("uid") Long uid,
                                         @Param("nickname") String nickname,
                                         @Param("deletedAt") LocalDateTime deletedAt);

    Optional<User> findUserByEmailAndDeletedAtIsNull(String email);
    Optional<User> findUserByEmail(String email);

    boolean existsByNicknameAndDeletedAtIsNull(String nickname);
    boolean existsByNickname(String nickname);

    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByDeletedAtIsNullAndOauth2UserInfo_OauthId(Long oauthId);
    Optional<User> findByOauth2UserInfo_OauthId(Long oauthId);
}