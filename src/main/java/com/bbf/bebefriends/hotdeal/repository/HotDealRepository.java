package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotDealRepository extends JpaRepository<HotDeal, Long> {

}
