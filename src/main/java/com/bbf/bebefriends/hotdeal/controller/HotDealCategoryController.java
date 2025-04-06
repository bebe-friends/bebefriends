package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.service.HotDealCategoryService;
import com.bbf.bebefriends.hotdeal.service.HotDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hot-deal-category")
public class HotDealCategoryController {

    private final HotDealCategoryService hotDealCategoryService;

    @PostMapping
    public HotDealCategoryDto createHotDealCategory(@RequestBody HotDealCategoryDto hotDealCategoryDto) {
        return hotDealCategoryService.createHotDealCategory(hotDealCategoryDto);

    }

}
