package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.global.repository.BaseDeleteRepository;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface HotDealPostRepository extends BaseDeleteRepository<HotDealPost, Long> {

    Page<HotDealPost> findAllByDeletedAtIsNull(Pageable pageable);

    Page<HotDealPost> findByHotDeal_HotDealCategoryAndDeletedAtIsNull(HotDealCategory hotDealCategory, Pageable pageable);

}
