package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotDealPostRepository extends JpaRepository<HotDealPost, Long> {

}
