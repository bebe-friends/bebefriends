package com.bbf.bebefriends.hotdeal.service.impl;

import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealRecordDto;
import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import com.bbf.bebefriends.hotdeal.entity.HotDealRecord;
import com.bbf.bebefriends.hotdeal.repository.HotDealCategoryRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealRecordRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealRepository;
import com.bbf.bebefriends.hotdeal.service.HotDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotDealServiceImpl implements HotDealService {

    private final HotDealRepository hotDealRepository;
    private final HotDealCategoryRepository hotDealCategoryRepository;
    private final HotDealRecordRepository hotDealRecordRepository;


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
                .hotDealCategory(hotDealCategory)
                .name(hotDealDto.getName())
                .imgPath(hotDealDto.getImgPath())
                .unit(hotDealDto.getUnit())
                .status(hotDealDto.getStatus())
                .build();
        hotDealRepository.save(hotDeal);

        return hotDealDto;
    }

    @Override
    public HotDealRecordDto createHotDealRecord(HotDealRecordDto hotDealRecordDto) {
        // 핫딜 조회
        HotDeal hotDeal = hotDealRepository.findById(hotDealRecordDto.getHotDealId())
                .orElseThrow();

        HotDealRecord hotDealRecord = HotDealRecord.builder()
                .hotDeal(hotDeal)
                .date(hotDealRecordDto.getDate())
                .note(hotDealRecordDto.getNote())
                .searchPrice(hotDealRecordDto.getSearchPrice())
                .hotDealPrice(hotDealRecordDto.getHotDealPrice())
                .build();
        hotDealRecordRepository.save(hotDealRecord);

        return hotDealRecordDto;
    }

    @Override
    public Page<HotDealRecordDto> searchHotDealRecord(Long hotDealId, Pageable pageable) {
        return hotDealRecordRepository.findByHotDeal_Id(hotDealId, pageable).map(HotDealRecordDto::fromEntity);
    }

}
