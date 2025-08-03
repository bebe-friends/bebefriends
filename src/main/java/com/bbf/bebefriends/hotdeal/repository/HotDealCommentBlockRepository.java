package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.hotdeal.entity.HotDealComment;
import com.bbf.bebefriends.hotdeal.entity.HotDealCommentBlock;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotDealCommentBlockRepository extends JpaRepository<HotDealCommentBlock, Long> {
    Boolean existsByUserAndComment(User user, HotDealComment comment);
}
