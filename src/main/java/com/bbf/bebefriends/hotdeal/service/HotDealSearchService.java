package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.global.entity.AgeRange;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.repository.HotDealPostRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class HotDealSearchService {

    private final HotDealRepository hotDealRepository;
    private final HotDealPostRepository hotDealPostRepository;
    private final HotDealCategoryService hotDealCategoryService;

    @Transactional(readOnly = true)
    public List<HotDealDto.HotDealSearchResponse> getAllHotDeals(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        List<HotDeal> hotDeals = hotDealRepository.findAll(pageRequest).getContent();

        return hotDeals.stream()
                .map(hotDeal -> {
                    // 최신 핫딜 게시글 1건 가져오기
                    HotDealPost hotDealPost = hotDealPostRepository.findFirstByHotDealAndDeletedAtIsNullOrderByCreatedDateDesc(hotDeal)
                            .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_POST_NOT_FOUND));

                    Set<AgeRange> ageRange = hotDealPost != null ? hotDealPost.getAgeRange() : Set.of();
                    Boolean status = hotDealPost != null ? hotDealPost.getStatus() : null;

                    return HotDealDto.HotDealSearchResponse.builder()
                            .id(hotDeal.getId())
                            .hotDealCategoryName(hotDeal.getHotDealCategory().getName())
                            .detailCategoryName(hotDeal.getDetailCategory().getName())
                            .imgPath(hotDeal.getImgPath())
                            .ageRange(ageRange)
                            .status(status)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HotDealDto.HotDealSearchResponse> getHotDealsByMainCategory(Long categoryId, int page, int size) {
        // 대분류 카테고리(depth=1) 확인
        HotDealCategory mainCategory = hotDealCategoryService.findByHotDealCategory(categoryId);
        if (mainCategory.getDepth() != 1) {
            throw new HotDealControllerAdvice(ResponseCode.HOTDEAL_INVALID_CATEGORY_DEPTH);
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // 대분류 카테고리에 속한 모든 핫딜 상품 조회
        List<HotDeal> hotDeals = hotDealRepository.findByHotDealCategoryId(categoryId, pageRequest);

        return hotDeals.stream()
                .map(hotDeal -> {
                    // 최신 핫딜 게시글 1건 가져오기
                    HotDealPost hotDealPost = hotDealPostRepository.findFirstByHotDealAndDeletedAtIsNullOrderByCreatedDateDesc(hotDeal)
                            .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_POST_NOT_FOUND));

                    Set<AgeRange> ageRange = hotDealPost != null ? hotDealPost.getAgeRange() : Set.of();
                    Boolean status = hotDealPost != null ? hotDealPost.getStatus() : null;

                    return HotDealDto.HotDealSearchResponse.builder()
                            .id(hotDeal.getId())
                            .hotDealCategoryName(hotDeal.getHotDealCategory().getName())
                            .detailCategoryName(hotDeal.getDetailCategory().getName())
                            .imgPath(hotDeal.getImgPath())
                            .ageRange(ageRange)
                            .status(status)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HotDealDto.HotDealSearchResponse> searchByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        List<HotDeal> hotDeals = hotDealRepository.findByNameContaining(name, pageRequest);

        return hotDeals.stream()
                .map(hotDeal -> {
                    // 최신 핫딜 게시글 1건 가져오기
                    HotDealPost hotDealPost = hotDealPostRepository.findFirstByHotDealAndDeletedAtIsNullOrderByCreatedDateDesc(hotDeal)
                            .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_POST_NOT_FOUND));

                    Set<AgeRange> ageRange = hotDealPost != null ? hotDealPost.getAgeRange() : Set.of();
                    Boolean status = hotDealPost != null ? hotDealPost.getStatus() : null;

                    return HotDealDto.HotDealSearchResponse.builder()
                            .id(hotDeal.getId())
                            .hotDealCategoryName(hotDeal.getHotDealCategory().getName())
                            .detailCategoryName(hotDeal.getDetailCategory().getName())
                            .imgPath(hotDeal.getImgPath())
                            .ageRange(ageRange)
                            .status(status)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HotDealDto.HotDealSearchResponse> searchByDetailCategory(String categoryName, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        List<HotDeal> hotDeals = hotDealRepository.findByDetailCategoryNameContaining(
                categoryName, pageRequest);

        return hotDeals.stream()
                .map(hotDeal -> {
                    // 최신 핫딜 게시글 1건 가져오기
                    HotDealPost hotDealPost = hotDealPostRepository.findFirstByHotDealAndDeletedAtIsNullOrderByCreatedDateDesc(hotDeal)
                            .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_POST_NOT_FOUND));

                    Set<AgeRange> ageRange = hotDealPost != null ? hotDealPost.getAgeRange() : Set.of();
                    Boolean status = hotDealPost != null ? hotDealPost.getStatus() : null;

                    return HotDealDto.HotDealSearchResponse.builder()
                            .id(hotDeal.getId())
                            .hotDealCategoryName(hotDeal.getHotDealCategory().getName())
                            .detailCategoryName(hotDeal.getDetailCategory().getName())
                            .imgPath(hotDeal.getImgPath())
                            .ageRange(ageRange)
                            .status(status)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HotDealDto.HotDealSearchResponse> searchByDetailCategoryId(Long categoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        List<HotDeal> hotDeals = hotDealRepository.findByDetailCategoryId(categoryId, pageRequest);

        return hotDeals.stream()
                .map(hotDeal -> {
                    // 최신 핫딜 게시글 1건 가져오기
                    HotDealPost hotDealPost = hotDealPostRepository.findFirstByHotDealAndDeletedAtIsNullOrderByCreatedDateDesc(hotDeal)
                            .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_POST_NOT_FOUND));

                    Set<AgeRange> ageRange = hotDealPost != null ? hotDealPost.getAgeRange() : Set.of();
                    Boolean status = hotDealPost != null ? hotDealPost.getStatus() : null;

                    return HotDealDto.HotDealSearchResponse.builder()
                            .id(hotDeal.getId())
                            .hotDealCategoryName(hotDeal.getHotDealCategory().getName())
                            .detailCategoryName(hotDeal.getDetailCategory().getName())
                            .imgPath(hotDeal.getImgPath())
                            .ageRange(ageRange)
                            .status(status)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
