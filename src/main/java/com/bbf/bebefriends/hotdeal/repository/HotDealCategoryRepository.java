package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotDealCategoryRepository extends JpaRepository<HotDealCategory, Long> {

    List<HotDealCategory> findByParentCategory_Id(Long parentId);
    List<HotDealCategory> findByDepth(Integer depth);

    @Query("SELECT c FROM hot_deal_category c WHERE c.name = :name AND c.parentCategory IS NULL")
    Optional<HotDealCategory> findByNameAndParentCategoryNull(String name);

    @Query("SELECT c FROM hot_deal_category c WHERE c.name = :name AND c.parentCategory = :parentCategory")
    Optional<HotDealCategory> findByNameAndParentCategory(String name, HotDealCategory parentCategory);

}
