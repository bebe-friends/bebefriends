package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
    // 특정 게시물의 댓글 수 (deletedAt NULL)
    int countByPostAndDeletedAtIsNull(CommunityPost post);
    List<CommunityComment> findByPostAndParentIsNullAndDeletedAtIsNull(CommunityPost post);
}
