package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.service.HotDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hot-deal")
public class HotDealController {

    private final HotDealService hotDealService;

    @PostMapping
    public HotDealDto createHotDeal(@RequestBody HotDealDto hotDealDto) {
        return hotDealService.createHotDeal(hotDealDto);

    }

}
