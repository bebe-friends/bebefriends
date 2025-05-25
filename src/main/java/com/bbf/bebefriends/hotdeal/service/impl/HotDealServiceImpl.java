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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public HotDealDto updateHotDeal(HotDealDto hotDealDto) {
        // 수정할 핫딜 조회
        HotDeal hotDeal = hotDealRepository.findById(hotDealDto.getId())
                .orElseThrow();

        // 기존과 다른 핫딜 카테고리인 경우
        if (!hotDeal.getHotDealCategory().getId().equals(hotDealDto.getHotDealCategoryId())) {
            // 수정된 핫딜 카테고리로 세팅
            HotDealCategory hotDealCategory = hotDealCategoryRepository.findById(hotDealDto.getHotDealCategoryId())
                    .orElseThrow();
            hotDeal.updateHotDealCategory(hotDealCategory);
        }

        // 핫딜 업데이트
        hotDeal.update(hotDealDto);

        return hotDealDto;
    }

    @Override
    public Long deleteHotDeal(Long hotDealId) {
        // 삭제할 핫딜 조회
        HotDeal hotDeal = hotDealRepository.findById(hotDealId)
                .orElseThrow();
        hotDealRepository.delete(hotDeal);

        return hotDealId;
    }

    @Override
    public Page<HotDealDto> searchAllHotDeal(Pageable pageable) {
        return hotDealRepository.findAll(pageable).map(HotDealDto::fromEntity);
    }

    @Override
    public Page<HotDealDto> searchCategoryHotDeal(Long hotDealCategoryId, Pageable pageable) {
        // 핫딜 카테고리 조회
        HotDealCategory hotDealCategory = hotDealCategoryRepository.findById(hotDealCategoryId)
                .orElseThrow();
        return hotDealRepository.findByHotDealCategory(hotDealCategory, pageable).map(HotDealDto::fromEntity);
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
    public HotDealRecordDto updateHotDealRecord(HotDealRecordDto hotDealRecordDto) {
        // 수정할 핫딜 기록 조회
        HotDealRecord hotDealRecord = hotDealRecordRepository.findById(hotDealRecordDto.getId())
                .orElseThrow();

        // 기존과 다른 핫딜인 경우
        if (!hotDealRecord.getHotDeal().getId().equals(hotDealRecordDto.getHotDealId())) {
            // 수정된 핫딜로 세팅
            HotDeal hotDeal = hotDealRepository.findById(hotDealRecordDto.getHotDealId())
                    .orElseThrow();
            hotDealRecord.updateHotDeal(hotDeal);
        }

        // 핫딜 기록 업데이트
        hotDealRecord.update(hotDealRecordDto);

        return hotDealRecordDto;
    }

    @Override
    public Long deleteHotDealRecord(Long hotDealRecordId) {
        // 삭제할 핫딜 기록 조회
        HotDealRecord hotDealRecord = hotDealRecordRepository.findById(hotDealRecordId)
                .orElseThrow();
        hotDealRecordRepository.delete(hotDealRecord);

        return hotDealRecordId;
    }

    @Override
    public Page<HotDealRecordDto> searchHotDealRecord(Long hotDealId, Pageable pageable) {
        return hotDealRecordRepository.findByHotDeal_Id(hotDealId, pageable).map(HotDealRecordDto::fromEntity);
    }

}
