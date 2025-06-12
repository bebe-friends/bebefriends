package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.entity.CommunityPostBlock;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostBlockRepository extends JpaRepository<CommunityPostBlock, Long> {
    Boolean existsByPostAndUser(CommunityPost post, User user);
}
