package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.global.repository.BaseDeleteRepository;
import com.bbf.bebefriends.hotdeal.entity.HotDealComment;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotDealCommentRepository extends BaseDeleteRepository<HotDealComment, Long> {

    Page<HotDealComment> findByHotDealPostAndDeletedAtIsNull(HotDealPost hotDealPost, Pageable pageable);

    Boolean existsByIdAndDeletedAtIsNotNull(Long commentId);

    @Query("""
      SELECT c
      FROM HotDealComment c
      WHERE c.hotDealPost = :post
        AND c.parentComment IS NULL
        AND c.deletedAt IS NULL
        AND ( :cursorId IS NULL OR c.id > :cursorId )
      ORDER BY c.id DESC
    """)
    List<HotDealComment> findParentCommentsWithCursor(
            @Param("post") HotDealPost post,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
      SELECT c
      FROM HotDealComment c
      WHERE c.parentComment = :parent
        AND c.deletedAt IS NULL
        AND ( :cursorId IS NULL OR c.id > :cursorId )
      ORDER BY c.id ASC
    """)
    List<HotDealComment> findChildCommentsWithCursor(
            @Param("parent")   HotDealComment parent,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
    long countByParentCommentAndDeletedAtIsNull(HotDealComment parent);
}
