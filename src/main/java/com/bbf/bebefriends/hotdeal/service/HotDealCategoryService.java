package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;

import java.util.List;

public interface HotDealCategoryService {

    HotDealCategoryDto createHotDealCategory(HotDealCategoryDto hotDealCategoryDto);

    HotDealCategoryDto updateHotDealCategory(HotDealCategoryDto hotDealCategoryDto);

    Long deleteHotDealCategory(Long hotDealCategoryId);

    List<HotDealCategoryDto> searchAllHotDealCategory();
}
