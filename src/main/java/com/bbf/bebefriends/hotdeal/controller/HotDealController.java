package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealRecordDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealRecord;
import com.bbf.bebefriends.hotdeal.service.HotDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hot-deal")
public class HotDealController {

    private final HotDealService hotDealService;

    @PostMapping
    public HotDealDto createHotDeal(@RequestBody HotDealDto hotDealDto) {
        return hotDealService.createHotDeal(hotDealDto);

    }

    @PostMapping("/record")
    public HotDealRecordDto createHotDealRecord(@RequestBody HotDealRecordDto hotDealRecordDto) {
        return hotDealService.createHotDealRecord(hotDealRecordDto);

    }

    @GetMapping("/record")
    public Page<HotDealRecord> searchHotDealRecord(@RequestBody HotDealRecordDto hotDealRecordDto, Pageable pageable) {
        return hotDealService.searchHotDealRecord(hotDealRecordDto, pageable);

    }

}
