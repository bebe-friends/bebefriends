package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.repository.HotDealCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotDealCategoryService {

    private final HotDealCategoryRepository hotDealCategoryRepository;

    public HotDealCategory findByHotDealCategory(Long hotDealCategoryId) {
        return hotDealCategoryRepository.findById(hotDealCategoryId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<HotDealCategoryDto> getSubCategoriesByParentId(Long parentId) {
        List<HotDealCategory> subCategories = hotDealCategoryRepository.findByParentCategory_Id(parentId);
        return subCategories.stream()
                .map(HotDealCategoryDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<HotDealCategoryDto> getAllMainCategories() {
        List<HotDealCategory> mainCategories = hotDealCategoryRepository.findByDepth(1);
        return mainCategories.stream()
                .map(HotDealCategoryDto::fromEntity)
                .toList();
    }
}
