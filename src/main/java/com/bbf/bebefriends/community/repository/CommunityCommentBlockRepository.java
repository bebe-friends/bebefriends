package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityCommentBlock;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.entity.CommunityPostBlock;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityCommentBlockRepository extends JpaRepository<CommunityCommentBlock, Long> {
    Boolean existsByUserAndComment(User user, CommunityComment comment);
    Optional<CommunityCommentBlock> findByCommentAndUser(CommunityComment comment, User user);
}
