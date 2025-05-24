package com.bbf.bebefriends.hotdeal.service.impl;

import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.repository.HotDealCategoryRepository;
import com.bbf.bebefriends.hotdeal.service.HotDealCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotDealCategoryServiceImpl implements HotDealCategoryService {

    private final HotDealCategoryRepository hotDealCategoryRepository;


    public HotDealCategoryDto createHotDealCategory(HotDealCategoryDto hotDealCategoryDto) {
        // 핫딜 카테고리 초기화
        HotDealCategory parentHotDealCategory = null;

        // 핫딜 카테고리 식별자가 있는 경우 핫딜 카테고리 조회
        if (hotDealCategoryDto.getParentCategoryId() != null) {
            parentHotDealCategory = hotDealCategoryRepository.findById(hotDealCategoryDto.getParentCategoryId())
                    .orElseThrow();
        }

        // 핫딜 카테고리 생성
        HotDealCategory hotDealCategory = HotDealCategory.builder()
                .parentCategory(parentHotDealCategory)
                .name(hotDealCategoryDto.getName())
                .depth(hotDealCategoryDto.getDepth())
                .build();
        hotDealCategoryRepository.save(hotDealCategory);

        return hotDealCategoryDto;
    }

    @Override
    public List<HotDealCategoryDto> searchAllHotDealCategory() {
        return hotDealCategoryRepository.findAll().stream().map(HotDealCategoryDto::fromEntity).toList();
    }

}
