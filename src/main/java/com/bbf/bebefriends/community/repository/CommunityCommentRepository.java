package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
    @Query("""
      SELECT c
      FROM CommunityComment c
      WHERE c.post = :post
        AND c.parent IS NULL
        AND ( :cursorId IS NULL OR c.id > :cursorId )
      ORDER BY c.id DESC
    """)
    List<CommunityComment> findParentCommentsWithCursor(
            @Param("post")     CommunityPost post,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
      SELECT c
      FROM CommunityComment c
      WHERE c.parent = :parent
        AND ( :cursorId IS NULL OR c.id > :cursorId )
      ORDER BY c.id ASC
    """)
    List<CommunityComment> findChildCommentsWithCursor(
            @Param("parent")   CommunityComment parent,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
    long countByParentAndDeletedAtIsNull(CommunityComment parent);

//    @Query(value = """
//    SELECT
//      p.comment_id           AS parentId,
//      p.user_id              AS parentUserId,
//      pu.nickname            AS parentAuthorName,
//      p.content              AS parentContent,
//      p.created_date         AS parentCreatedDate,
//
//      r.comment_id           AS childId,
//      r.user_id              AS childUserId,
//      cu.nickname            AS childAuthorName,
//      r.content              AS childContent,
//      r.created_date         AS childCreatedDate
//    FROM community_comments p
//      LEFT JOIN community_comments r
//        ON r.parent_id = p.comment_id
//        AND r.deleted_at IS NULL
//      LEFT JOIN users pu
//        ON pu.uid = p.user_id
//      LEFT JOIN users cu
//        ON cu.uid = r.user_id
//    WHERE p.post_id        = :postId
//      AND p.parent_id      IS NULL
//      AND p.deleted_at     IS NULL
//      AND (
//            (:primaryOffset IS NULL)
//         OR ( p.comment_id > :primaryOffset )
//         OR ( p.comment_id = :primaryOffset AND r.comment_id > COALESCE(:subOffset, 0) )
//          )
//    ORDER BY p.comment_id ASC, r.comment_id ASC
//    LIMIT :limit
//    """, nativeQuery = true
//    )
//    List<CommunityCommentDTO.CommentCursorProjection> findCommentByCursor(
//            @Param("postId")        Long postId,
//            @Param("primaryOffset") Long primaryOffset,  // 마지막으로 본 parent.comment_id
//            @Param("subOffset")     Long subOffset,      // 동일 parent 아래 마지막으로 본 child.comment_id
//            @Param("limit")         int   limit          // 페이지 크기
//    );

    List<CommunityComment> findAllByDeletedAtBefore(LocalDateTime threshold);
    Boolean existsByIdAndDeletedAtIsNotNull(Long commentId);
}
