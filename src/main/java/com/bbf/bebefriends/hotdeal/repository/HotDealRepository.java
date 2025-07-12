package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotDealRepository extends JpaRepository<HotDeal, Long> {

    Page<HotDeal> findByHotDealCategory(HotDealCategory hotDealCategory, Pageable pageable);
    boolean existsByHotDealCategoryOrDetailCategory(HotDealCategory category, HotDealCategory detailCategory);

    @Query("SELECT h FROM hot_deal h " +
            "JOIN FETCH h.detailCategory dc " +
            "WHERE dc.name LIKE %:categoryName% " +
            "ORDER BY h.createdDate DESC")
    List<HotDeal> findByDetailCategoryNameContaining(
            @Param("categoryName") String categoryName,
            Pageable pageable
    );

    @Query("SELECT h FROM hot_deal h " +
            "WHERE h.detailCategory.id = :categoryId " +
            "ORDER BY h.createdDate DESC")
    List<HotDeal> findByDetailCategoryId(
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    List<HotDeal> findByNameContaining(String name, Pageable pageable);
    List<HotDeal> findByHotDealCategoryId(Long categoryId, Pageable pageable);
}
