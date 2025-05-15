package com.bbf.bebefriends.member.repository;


import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // soft delete 된 유저 제외
    Optional<User> findByUidAndDeletedAtIsNull(Long uid);

    Optional<User> findUserByEmail(String email);

    boolean existsByNicknameAndDeletedAtIsNull(String nickname);
}