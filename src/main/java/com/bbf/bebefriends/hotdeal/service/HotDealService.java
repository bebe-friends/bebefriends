package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealRecordDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotDealService {

    HotDealDto createHotDeal(HotDealDto hotDealDto);

    HotDealDto updateHotDeal(HotDealDto hotDealDto);

    Long deleteHotDeal(Long hotDealId);

    Page<HotDealDto> searchAllHotDeal(Pageable pageable);

    Page<HotDealDto> searchCategoryHotDeal(Long hotDealCategoryId, Pageable pageable);

    HotDealRecordDto createHotDealRecord(HotDealRecordDto hotDealRecordDto);

    HotDealRecordDto updateHotDealRecord(HotDealRecordDto hotDealRecordDto);

    Long deleteHotDealRecord(Long hotDealRecordId);

    Page<HotDealRecordDto> searchHotDealRecord(Long hotDealId, Pageable pageable);
}
