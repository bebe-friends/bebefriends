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

// FIXME: 페이지네이션 필요
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
        )
    """)
    List<CommunityPost> findAllActivePosts();

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
        )
    """)
    List<CommunityPost> findByCategoryActive(@Param("category") CommunityCategory category);

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
          )
    """)
    List<CommunityPost> searchPostsByKeyword(@Param("kw") String keyword);

    // 제목으로 조회(deletedAt과 isReported가 null)
//    @Query("""
//      SELECT p
//      FROM CommunityPost p
//      WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :kw, '%'))
//        AND p.deletedAt IS NULL
//        AND p.isReported IS NULL
//        AND p.id NOT IN (
//          SELECT b.post.id
//          FROM CommunityPostBlock b
//        )
//    """)
//    List<CommunityPost> findByTitleLikeActive(@Param("kw") String keyword);


    // 닉네임으로 조회(deletedAt과 isReported가 null)
//    @Query("""
//      SELECT p
//      FROM CommunityPost p
//      JOIN p.user u
//      WHERE LOWER(u.nickname) LIKE LOWER(CONCAT('%', :kw, '%'))
//        AND p.deletedAt IS NULL
//        AND p.isReported IS NULL
//        AND p.id NOT IN (
//          SELECT b.post.id
//          FROM CommunityPostBlock b
//        )
//    """)
//    List<CommunityPost> findByAuthorLikeActive(@Param("kw") String keyword);


    // 작성한 게시물 목록(deletedAt가 null) -> 신고당한 게시물 삭제처리해야하나?
    List<CommunityPost> findByUserAndDeletedAtIsNull(User user);

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
          FROM CommunityPostBlock b)
    """)
    List<CommunityPost> findCommentedByUserActive(@Param("user") User user);


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
          FROM CommunityPostBlock b)
    """)
    List<CommunityPost> findLikedByUserActive(@Param("user") User user);

    // 댓글을 따로 만들어서 비동기처리해야하나?
    // 게시물 상세 페이지
    
}
