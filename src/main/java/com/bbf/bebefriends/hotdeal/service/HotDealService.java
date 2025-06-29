package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.repository.HotDealRepository;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotDealService {

    private final HotDealRepository hotDealRepository;
    private final HotDealCategoryService hotDealCategoryService;

    public HotDeal findByHotDeal(Long hotDealId) {
        return hotDealRepository.findById(hotDealId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_NOT_FOUND));
    }

    @Transactional
    public void createHotDeal(
            User user, HotDealDto.HotDealRequest request, List<MultipartFile> images
    ) {
        // 새로 핫딜 추가할때는 무조건 기존에 등록되어 있는 대카테고리 항목에 매칭되어야 함
        HotDealCategory hotDealCategory =
                hotDealCategoryService.findByHotDealCategory(request.hotDealCategoryId());

        HotDeal hotDeal = HotDeal.createHotDeal(user, hotDealCategory, request, images);
        hotDealRepository.save(hotDeal);
    }

    @Transactional
    public void updateHotDealStatus(User user, HotDealDto.HotDealStatusRequest request) {
        HotDeal hotDeal = findByHotDeal(request.id());
        if (!hotDeal.getUser().getUid().equals(user.getUid())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

//        hotDeal.setStatus(request.status());
        hotDealRepository.save(hotDeal);
    }

}
