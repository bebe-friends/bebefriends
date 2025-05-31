package com.bbf.bebefriends.hotdeal.service.impl;

import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.repository.HotDealCategoryRepository;
import com.bbf.bebefriends.hotdeal.service.HotDealCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public HotDealCategoryDto updateHotDealCategory(HotDealCategoryDto hotDealCategoryDto) {
        // 수정할 핫딜 카테고리 조회
        HotDealCategory hotDealCategory = hotDealCategoryRepository.findById(hotDealCategoryDto.getId())
                .orElseThrow();


        // 핫딜 카테고리가 없는 경우 (대분류인 카테고리 수정)
        if (hotDealCategoryDto.getParentCategoryId() == null) {
            // 핫딜 카테고리 업데이트
            hotDealCategory.update(hotDealCategoryDto);
            return hotDealCategoryDto;

        }

        // 기존과 다른 상위 핫딜 카테고리인 경우
        if (!hotDealCategory.getParentCategory().getId().equals(hotDealCategoryDto.getParentCategoryId())) {
            // 수정된 상위 핫딜 카테고리로 세팅
            HotDealCategory parentCategory = hotDealCategoryRepository.findById(hotDealCategoryDto.getParentCategoryId())
                    .orElseThrow();
            hotDealCategory.updateParentCategory(parentCategory);
        }

        // 핫딜 카테고리 업데이트
        hotDealCategory.update(hotDealCategoryDto);

        return hotDealCategoryDto;
    }

    @Override
    public Long deleteHotDealCategory(Long hotDealCategoryId) {
        // 삭제할 핫딜 카테고리 조회
        HotDealCategory hotDealCategory = hotDealCategoryRepository.findById(hotDealCategoryId)
                .orElseThrow();
        hotDealCategoryRepository.delete(hotDealCategory);

        return hotDealCategoryId;
    }

    @Override
    public List<HotDealCategoryDto> searchAllHotDealCategory() {
        return hotDealCategoryRepository.findAll().stream().map(HotDealCategoryDto::fromEntity).toList();
    }

}
