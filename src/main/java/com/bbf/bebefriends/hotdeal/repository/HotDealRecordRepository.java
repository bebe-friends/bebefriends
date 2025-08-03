package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.hotdeal.entity.HotDealRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HotDealRecordRepository extends JpaRepository<HotDealRecord, Long> {

    Page<HotDealRecord> findByHotDeal_Id(Long hotDealId, Pageable pageable);

    @Query("SELECT hr FROM hot_deal_record hr WHERE hr.hotDeal.id = :hotDealId")
    Page<HotDealRecord> findAllByHotDealId(@Param("hotDealId") Long hotDealId, Pageable pageable);


}
