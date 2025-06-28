package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto.CategoryRequest;
import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto.HotDealResponse;
import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.repository.HotDealCategoryRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotDealCategoryService {

    private final HotDealCategoryRepository hotDealCategoryRepository;
    private final HotDealRepository hotDealRepository;

    public HotDealCategory findByHotDealCategory(Long hotDealCategoryId) {
        return hotDealCategoryRepository.findById(hotDealCategoryId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<HotDealResponse> getSubCategoriesByParentId(Long parentId) {
        List<HotDealCategory> subCategories = hotDealCategoryRepository.findByParentCategory_Id(parentId);

        if (subCategories.isEmpty()) {
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_SUB_CATEGORY_NOT_FOUND);
        }

        return subCategories.stream()
                .map(category -> new HotDealResponse(
                        category.getId(),
                        category.getParentCategory() != null ? category.getParentCategory().getId() : null,
                        category.getName(),
                        category.getDepth()
                )).toList();
    }

    @Transactional(readOnly = true)
    public List<HotDealResponse> getAllMainCategories() {
        List<HotDealCategory> mainCategories = hotDealCategoryRepository.findByDepth(1);
        return mainCategories.stream()
                .map(category -> new HotDealResponse(
                        category.getId(),
                        category.getParentCategory() != null ? category.getParentCategory().getId() : null,
                        category.getName(),
                        category.getDepth()
                )).toList();
    }

    @Transactional
    public void createOrFindCategoriesByNames(CategoryRequest request) {
        // 중분류 생성/탐색
        HotDealCategory middleCategory = findOrCreateCategory(request.middleCategory(), null);

        // 소분류 생성/탐색
        HotDealCategory smallCategory = findOrCreateCategory(request.smallCategory(), middleCategory);

        // 세분류 생성/탐색
        HotDealCategory detailedCategory = findOrCreateCategory(request.detailedCategory(), smallCategory);

        // 핫딜과 매칭
        matchHotDealToCategory(request.hotDealId(), detailedCategory);
    }

    /**
     * 이름과 부모 카테고리를 기준으로 카테고리를 찾거나 생성
     */
    private HotDealCategory findOrCreateCategory(String name, HotDealCategory parentCategory) {
        if (name == null || name.trim().isEmpty()) {
            log.error("카테고리 이름을 입력하세요.");
            throw new HotDealControllerAdvice(ResponseCode._BAD_REQUEST);
        }

        if (parentCategory != null && !hotDealCategoryRepository.existsById(parentCategory.getId())) {
            log.error("부모 카테고리가 유효하지 않습니다.");
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND);
        }

        // 부모 카테고리가 없는 경우
        if (parentCategory == null) {
            return hotDealCategoryRepository.findByNameAndParentCategoryNull(name)
                    .orElseGet(() -> createCategory(name, null));
        }

        // 부모 카테고리 아래에서 탐색
        return hotDealCategoryRepository.findByNameAndParentCategory(name, parentCategory)
                .orElseGet(() -> createCategory(name, parentCategory));
    }

    /**
     * 새 카테고리 생성
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected HotDealCategory createCategory(String name, HotDealCategory parentCategory) {
        int depth = (parentCategory == null) ? 1 : parentCategory.getDepth() + 1;

        return hotDealCategoryRepository.save(
                HotDealCategory.builder()
                        .name(name)
                        .parentCategory(parentCategory)
                        .depth(depth)
                        .build()
        );
    }

    private void matchHotDealToCategory(Long hotDealId, HotDealCategory category) {
        HotDeal hotDeal = hotDealRepository.findById(hotDealId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_NOT_FOUND));

        if (hotDeal.getHotDealCategory() != null) {
            log.warn("핫딜 {}은 이미 카테고리 {}와 연결되어 있습니다.", hotDealId, hotDeal.getHotDealCategory().getId());
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_ALREADY_MATCHED);
        }

        hotDeal.setHotDealCategory(category);
        hotDealRepository.save(hotDeal);

    }

}
