package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// TODO: 페이지네이션 (좋아요 많은 순으로 정렬 등 여러 페이지네이션 고려)
//  not exist 로 쿼리 조건문 변경, 카테고리 파라미터를 id 값으로 변경, 비로그인 대상 메서드를 쿼리문으로 처리(querydsl)
@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    // 전체 게시글 조회(deletedAt과 isReported가 null인 전체 항목)
    @Query("""
      SELECT p
      FROM CommunityPost p
      WHERE p.deletedAt IS NULL
        AND p.isReported IS NULL
        AND p.id NOT IN (
            SELECT b.post.id
            FROM CommunityPostBlock b
            WHERE b.user = :user
        )
        AND ( :cursorId IS NULL OR p.id < :cursorId )
      ORDER BY p.id DESC
    """)
    List<CommunityPost> findAllActivePostsWithCursor(
            @Param("user") User user,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );


    // 카테고리 별 조회(deletedAt과 isReported가 null)
    @Query("""
      SELECT p
      FROM CommunityPost p
      WHERE p.category       = :category
        AND p.deletedAt      IS NULL
        AND p.isReported     IS NULL
        AND p.id NOT IN (
          SELECT b.post.id
          FROM CommunityPostBlock b
          WHERE b.user = :user
        )
        AND ( :cursorId IS NULL OR p.id < :cursorId )
      ORDER BY p.id DESC
    """)
    List<CommunityPost> findByCategoryActive(@Param("category") CommunityCategory category,
                                             @Param("user") User user,
                                             @Param("cursorId") Long cursorId,
                                             Pageable pageable);

    // 제목 혹은 글쓴이로 검색
    @Query("""
        SELECT p
        FROM CommunityPost p
        JOIN p.user u
        WHERE (LOWER(p.title)   LIKE LOWER(CONCAT('%', :kw, '%'))
            OR LOWER(u.nickname) LIKE LOWER(CONCAT('%', :kw, '%')))
          AND p.deletedAt   IS NULL
          AND p.isReported  IS NULL
          AND p.id NOT IN (
              SELECT b.post.id
              FROM CommunityPostBlock b
              WHERE b.user = :user
          )
          AND ( :cursorId IS NULL OR p.id < :cursorId )
      ORDER BY p.id DESC
    """)
    List<CommunityPost> searchPostsByKeyword(@Param("kw") String keyword,
                                             @Param("user") User user,
                                             @Param("cursorId") Long cursorId,
                                             Pageable pageable);

    // 작성한 게시물 목록(deletedAt가 null) -> 신고당한 게시물 삭제처리해야하나?
//    List<CommunityPost> findByUserAndDeletedAtIsNull(User user);

    @Query("""
      SELECT p
      FROM CommunityPost p
      WHERE p.user        = :user
        AND p.deletedAt   IS NULL
        AND ( :cursorId IS NULL OR p.id < :cursorId )
      ORDER BY p.id DESC
    """)
    List<CommunityPost> findMyPostsWithCursor(
            @Param("user")     User user,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
    // 댓글단 게시물 목록(deletedAt과 isReported가 null)
    @Query("""
      SELECT DISTINCT p
      FROM CommunityPost p
      JOIN CommunityComment c ON c.post = p
      WHERE c.user = :user
        AND p.deletedAt IS NULL
        AND p.isReported IS NULL
        AND p.id NOT IN (
          SELECT b.post.id
          FROM CommunityPostBlock b
          WHERE b.user = :user
          )
        AND ( :cursorId IS NULL OR p.id < :cursorId )
      ORDER BY p.id DESC
    """)
    List<CommunityPost> findCommentedByUserActive(@Param("user") User user,
                                                  @Param("cursorId") Long cursorId,
                                                  Pageable pageable);


    // 좋아요한 게시물 목록(deletedAt과 isReported가 null)
    @Query("""
      SELECT p
      FROM CommunityPost p
      JOIN CommunityPostLike l ON l.post = p
      WHERE l.user = :user
        AND p.deletedAt IS NULL
        AND p.isReported IS NULL
        AND p.id NOT IN (
          SELECT b.post.id
          FROM CommunityPostBlock b
          WHERE b.user = :user
          )
        AND ( :cursorId IS NULL OR p.id < :cursorId )
      ORDER BY p.id DESC
    """)
    List<CommunityPost> findLikedByUserActive(@Param("user") User user,
                                              @Param("cursorId") Long cursorId,
                                              Pageable pageable);

    // 댓글을 따로 만들어서 비동기처리해야하나?
    // 게시물 상세 페이지
    
}
