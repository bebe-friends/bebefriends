package com.bbf.bebefriends.hotdeal.service.impl;

import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.repository.HotDealCategoryRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealRepository;
import com.bbf.bebefriends.hotdeal.service.HotDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotDealServiceImpl implements HotDealService {

    private final HotDealRepository hotDealRepository;
    private final HotDealCategoryRepository hotDealCategoryRepository;


    public HotDealDto createHotDeal(HotDealDto hotDealDto) {
        // 핫딜 카테고리 초기화
        HotDealCategory hotDealCategory = null;

        // 핫딜 카테고리 식별자가 있는 경우 핫딜 카테고리 조회
        if (hotDealDto.getHotDealCategoryId() != null) {
            hotDealCategory = hotDealCategoryRepository.findById(hotDealDto.getHotDealCategoryId())
                    .orElseThrow();
        }

        // 핫딜 생성
        HotDeal hotDeal = HotDeal.builder()
                .hotDealCategoryId(hotDealCategory)
                .name(hotDealDto.getName())
                .imgPath(hotDealDto.getImgPath())
                .unit(hotDealDto.getUnit())
                .status(hotDealDto.getStatus())
                .build();
        hotDealRepository.save(hotDeal);

        return hotDealDto;
    }

}
