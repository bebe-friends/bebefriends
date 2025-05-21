package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealRecordDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotDealService {

    HotDealDto createHotDeal(HotDealDto hotDealDto);

    HotDealRecordDto createHotDealRecord(HotDealRecordDto hotDealRecordDto);

    Page<HotDealRecordDto> searchHotDealRecord(Long hotDealId, Pageable pageable);
}
