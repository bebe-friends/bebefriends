package com.bbf.bebefriends.member.repository;


import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // soft delete 된 유저 제외
    Optional<User> findByUidAndDeletedAtIsNull(String uid);

    List<User> findAllByDeletedAtIsNull();

    boolean existsByNicknameAndDeletedAtIsNull(String nickname);

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND u.email = :email")
    Optional<User> findActiveByEmail(@Param("email") String email);
}