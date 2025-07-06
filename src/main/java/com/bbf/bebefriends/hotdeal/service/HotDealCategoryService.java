package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;
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
    public void matchCategoriesWithHotDeal(CategoryRequest request) {
        HotDealCategory mainCategory = hotDealRepository.findById(request.hotDealId())
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_NOT_FOUND))
                .getHotDealCategory();

        // 중분류 탐색
        HotDealCategory middleCategory = findCategory(request.middleCategory(), mainCategory);

        // 소분류 탐색
        HotDealCategory smallCategory = findCategory(request.smallCategory(), middleCategory);

        // 세분류 탐색
        HotDealCategory detailedCategory = findCategory(request.detailedCategory(), smallCategory);

        // 핫딜과 매칭
        matchHotDealToCategory(request.hotDealId(), detailedCategory);
    }

    /**
     * 이름과 부모 카테고리를 기준으로 카테고리를 찾음
     */
    private HotDealCategory findCategory(String name, HotDealCategory parentCategory) {
        if (name == null || name.trim().isEmpty()) {
            log.error("카테고리 이름을 입력하세요.");
            throw new HotDealControllerAdvice(ResponseCode._BAD_REQUEST);
        }

        if (parentCategory != null && !hotDealCategoryRepository.existsById(parentCategory.getId())) {
            log.error("부모 카테고리가 유효하지 않습니다.");
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND);
        }

        // 부모 카테고리 아래에서 탐색하고 없으면 예외 발생
        return hotDealCategoryRepository.findByNameAndParentCategory(name, parentCategory)
                .orElseThrow(() -> {
                    log.error("카테고리를 찾을 수 없습니다: {}", name);
                    return new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND);
                });
    }

    private void matchHotDealToCategory(Long hotDealId, HotDealCategory category) {
        HotDeal hotDeal = hotDealRepository.findById(hotDealId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_NOT_FOUND));

        if (hotDeal.getDetailCategory() != null) {
            log.warn("핫딜 {}은 이미 카테고리 {}와 연결되어 있습니다.", hotDealId, hotDeal.getHotDealCategory().getId());
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_ALREADY_MATCHED);
        }

        hotDeal.setDetailCategory(category);
        hotDealRepository.save(hotDeal);

    }

    @Transactional
    public void createNewCategory(HotDealCategoryDto.HotDealCategoryRequest request) {
        // 상위 카테고리 조회
        HotDealCategory parentCategory = null;
        if (request.parentCategoryId() != null) {
            parentCategory = hotDealCategoryRepository.findById(Long.parseLong(request.parentCategoryId()))
                    .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND));
        }

        // 동일한 이름의 카테고리가 이미 존재하는지 확인
        if (hotDealCategoryRepository.findByNameAndParentCategory(
                request.targetCategory(), parentCategory).isPresent()) {
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_ALREADY_EXISTS);
        }

        // 새 카테고리의 depth 계산
        int depth = (parentCategory == null) ? 1 : parentCategory.getDepth() + 1;

        // depth 유효성 검사 (1~4 범위)
        if (depth < 1 || depth > 4) {
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_INVALID_CATEGORY_DEPTH);
        }

        // 새 카테고리 생성
        HotDealCategory newCategory = HotDealCategory.builder()
                .name(request.targetCategory())
                .parentCategory(parentCategory)
                .depth(depth)
                .build();

        hotDealCategoryRepository.save(newCategory);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public void updateCategory(HotDealCategoryDto.CategoryUpdateRequest request) {
        HotDealCategory category = hotDealCategoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND));

        // 같은 부모 카테고리 내에 동일한 이름이 있는지 확인
        if (hotDealCategoryRepository.findByNameAndParentCategory(
                request.newName(), category.getParentCategory()).isPresent()) {
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_ALREADY_EXISTS);
        }

        category.setName(request.newName());
        hotDealCategoryRepository.save(category);
    }

    /**
     * 카테고리 삭제
     * 하위 카테고리가 있거나 연결된 핫딜이 있는 경우 삭제 불가
     */
    @Transactional
    public void deleteCategory(Long categoryId) {
        HotDealCategory category = hotDealCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_NOT_FOUND));

        // 하위 카테고리 존재 여부 확인
        if (hotDealCategoryRepository.existsByParentCategory(category)) {
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_HAS_SUBCATEGORIES);
        }

        // 연결된 핫딜 존재 여부 확인
        if (hotDealRepository.existsByHotDealCategoryOrDetailCategory(category, category)) {
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_CATEGORY_IN_USE);
        }

        hotDealCategoryRepository.delete(category);
    }

}
