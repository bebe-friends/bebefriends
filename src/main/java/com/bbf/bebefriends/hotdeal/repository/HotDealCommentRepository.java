package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.global.repository.BaseDeleteRepository;
import com.bbf.bebefriends.hotdeal.entity.HotDealComment;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotDealCommentRepository extends BaseDeleteRepository<HotDealComment, Long> {

    Page<HotDealComment> findByHotDealPostAndDeletedAtIsNull(HotDealPost hotDealPost, Pageable pageable);
}
