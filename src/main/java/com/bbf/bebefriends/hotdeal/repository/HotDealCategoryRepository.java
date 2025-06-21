package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotDealCategoryRepository extends JpaRepository<HotDealCategory, Long> {

    List<HotDealCategory> findByParentCategory_Id(Long parentId);
    List<HotDealCategory> findByDepth(Integer depth);

}
