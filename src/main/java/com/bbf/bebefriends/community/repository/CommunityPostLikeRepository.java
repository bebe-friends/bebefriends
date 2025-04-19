package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.entity.CommunityPostLike;
import com.bbf.bebefriends.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityPostLikeRepository extends JpaRepository<CommunityPostLike, Long> {
    List<CommunityPostLike> findByMember(Member member);
    Optional<CommunityPostLike> findByPostAndMember(CommunityPost post, Member member);

}
