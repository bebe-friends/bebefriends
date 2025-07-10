package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.entity.HotDealRecord;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.repository.HotDealRecordRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealRepository;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotDealService {

    private final HotDealRepository hotDealRepository;
    private final HotDealCategoryService hotDealCategoryService;
    private final HotDealRecordRepository hotDealRecordRepository;

    public HotDeal findByHotDeal(Long hotDealId) {
        return hotDealRepository.findById(hotDealId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_NOT_FOUND));
    }

    @Transactional
    public void createHotDeal(
            User user, HotDealDto.HotDealRequest request, List<MultipartFile> images
    ) {
        // 새로 핫딜 추가할때는 무조건 기존에 등록되어 있는 대 카테고리, 세분류 항목에 매칭되어야 함
        HotDealCategory hotDealCategory =
                hotDealCategoryService.findByHotDealCategory(request.hotDealCategoryId());

        HotDealCategory detailCategory =
                hotDealCategoryService.findByHotDealCategory(request.detailCategoryId());

        HotDeal hotDeal = HotDeal.createHotDeal(user, hotDealCategory, detailCategory, request, images);
        hotDealRepository.save(hotDeal);

// 핫딜 기록 같이 추가
        HotDealRecord hotDealRecord = HotDealRecord.createHotDealRecord(
                hotDeal,
                request.note(),
                request.searchPrice(),
                request.hotDealPrice()
        );
        hotDealRecordRepository.save(hotDealRecord);
    }

    @Transactional(readOnly = true)
    public List<HotDealDto.HotDealSearchResponse> searchByDetailCategory(String categoryName, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        List<HotDeal> hotDeals = hotDealRepository.findByDetailCategoryNameContaining(
                categoryName, pageRequest);

        return hotDeals.stream()
                .map(hotDeal -> new HotDealDto.HotDealSearchResponse(
                        hotDeal.getId(),
                        hotDeal.getName(),
                        hotDeal.getContent(),
                        new HotDealDto.HotDealSearchResponse.CategoryInfo(
                                hotDeal.getDetailCategory().getId(),
                                hotDeal.getDetailCategory().getName(),
                                hotDeal.getDetailCategory().getDepth()
                        ),
                        hotDeal.getCreatedDate()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HotDealDto.HotDealSearchResponse> searchByDetailCategoryId(Long categoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        List<HotDeal> hotDeals = hotDealRepository.findByDetailCategoryId(categoryId, pageRequest);

        return hotDeals.stream()
                .map(hotDeal -> new HotDealDto.HotDealSearchResponse(
                        hotDeal.getId(),
                        hotDeal.getName(),
                        hotDeal.getContent(),
                        new HotDealDto.HotDealSearchResponse.CategoryInfo(
                                hotDeal.getDetailCategory().getId(),
                                hotDeal.getDetailCategory().getName(),
                                hotDeal.getDetailCategory().getDepth()
                        ),
                        hotDeal.getCreatedDate()
                ))
                .collect(Collectors.toList());
    }

}
