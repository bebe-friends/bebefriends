package com.bbf.bebefriends.hotdeal.service;


import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealRecordDto;
import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import com.bbf.bebefriends.hotdeal.entity.HotDealRecord;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.repository.HotDealRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotDealRecordService {

    private final HotDealService hotDealService;
    private final HotDealRecordRepository hotDealRecordRepository;

    public HotDealRecord findByHotDealRecord(Long hotDealRecordId) {
        return hotDealRecordRepository.findById(hotDealRecordId)
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode.HOTDEAL_RECORD_NOT_FOUND));
    }

    @Transactional
    public void createHotDealRecord(HotDealRecordDto.HotDealRecordRequest request) {
        HotDeal hotDeal = hotDealService.findByHotDeal(request.hotDealId());

        HotDealRecord hotDealRecord = HotDealRecord.builder()
                .hotDeal(hotDeal)
                .date(request.date())
                .note(request.note())
                .searchPrice(request.searchPrice())
                .hotDealPrice(request.hotDealPrice())
                .build();

        hotDealRecordRepository.save(hotDealRecord);
    }

    @Transactional(readOnly = true)
    public List<HotDealRecordDto.HotDealRecordResponse> getHotDealRecordTopList(Long hotDealId) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "date"));
        Page<HotDealRecord> records = hotDealRecordRepository.findByHotDeal_Id(hotDealId, pageable);

        return records.stream()
                .map(record ->
                        new HotDealRecordDto.HotDealRecordResponse(
                                record.getSearchPrice(),
                                record.getHotDealPrice(),
                                record.getNote(),
                                record.getDate()
                        )
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public HotDealRecordDto.HotDealRecordDetailResponse getHotDealRecordDetail(Long hotDealRecordId) {
        HotDealRecord record = findByHotDealRecord(hotDealRecordId);
        return new HotDealRecordDto.HotDealRecordDetailResponse(
                record.getId(),
                record.getHotDeal().getId(),
                record.getDate(),
                record.getNote(),
                record.getSearchPrice(),
                record.getHotDealPrice()
        );
    }

    @Transactional
    public void updateHotDealRecord(Long hotDealRecordId, HotDealRecordDto.HotDealRecordUpdateRequest request) {
        HotDealRecord record = findByHotDealRecord(hotDealRecordId);

        record.update(
                request.date(),
                request.note(),
                request.searchPrice(),
                request.hotDealPrice()
        );

        hotDealRecordRepository.save(record);
    }

    @Transactional
    public void deleteHotDealRecord(Long hotDealRecordId) {
        HotDealRecord record = findByHotDealRecord(hotDealRecordId);
        hotDealRecordRepository.delete(record);
    }

}
