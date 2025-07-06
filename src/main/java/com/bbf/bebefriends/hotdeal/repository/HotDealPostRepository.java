package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.global.repository.BaseDeleteRepository;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface HotDealPostRepository extends BaseDeleteRepository<HotDealPost, Long> {
//    Page<HotDealPost> findAllByDeletedAtIsNull(Pageable pageable);
//
//    Page<HotDealPost> findByHotDeal_HotDealCategoryAndDeletedAtIsNull(HotDealCategory hotDealCategory, Pageable pageable);

    // 전체 핫딜 조회
    @Query("""
            SELECT p
            FROM HotDealPost p
            WHERE p.deletedAt    IS NULL
              AND ( :cursorId IS NULL OR p.id < :cursorId )
            ORDER BY p.id DESC
            """)
    List<HotDealPost> findAllHotDealsWithCursor(
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    // 맞춤 핫딜 조회
    @Query("""
            SELECT p
            FROM HotDealPost p
            JOIN UserHotdealNotification u
              ON u.user = :user
            JOIN u.preferredCategories c
            WHERE p.deletedAt IS NULL
              AND p.hotDealCategory = c
              AND ( :cursorId IS NULL OR p.id < :cursorId )
            ORDER BY p.id DESC
            """)
    List<HotDealPost> findCustomHotDealsWithCursor(
            @Param("user") User user,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
