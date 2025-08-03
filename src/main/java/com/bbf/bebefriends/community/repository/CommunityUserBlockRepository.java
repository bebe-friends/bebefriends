package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityUserBlock;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityUserBlockRepository extends JpaRepository<CommunityUserBlock, Long> {
    Optional<CommunityUserBlock> findByUserAndBlockedUser(User user, User blockedUser);
    boolean existsByUserAndBlockedUser(User user, User blockedUser);
}
