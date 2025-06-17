package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityPostForAnonymousRepository extends JpaRepository<CommunityPost, Long> {
    @Query("""
      SELECT p
      FROM CommunityPost p
      WHERE p.deletedAt IS NULL
        AND p.isReported IS NULL
        AND ( :cursorId IS NULL OR p.id < :cursorId )
      ORDER BY p.id DESC
    """)
    List<CommunityPost> findAllActivePostsForAnonymous(@Param("cursorId") Long cursorId,
                                                       Pageable pageable);

    // 카테고리 별 조회(deletedAt과 isReported가 null)
    @Query("""
      SELECT p
      FROM CommunityPost p
      WHERE p.category       = :category
        AND p.deletedAt      IS NULL
        AND p.isReported     IS NULL
        AND ( :cursorId IS NULL OR p.id < :cursorId )
      ORDER BY p.id DESC
    """)
    List<CommunityPost> findByCategoryActiveForAnonymous(@Param("category") CommunityCategory category,
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
          AND ( :cursorId IS NULL OR p.id < :cursorId )
      ORDER BY p.id DESC
    """)
    List<CommunityPost> searchPostsByKeywordForAnonymous(@Param("kw") String keyword,
                                                         @Param("cursorId") Long cursorId,
                                                         Pageable pageable);
}
