package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealRecordDto;
import com.bbf.bebefriends.hotdeal.service.HotDealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "핫딜", description = "핫딜 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hot-deal")
public class HotDealController {

    private final HotDealService hotDealService;

    @Operation(summary = "핫딜 등록", description = "핫딜을 등록합니다.")
    @PostMapping
    public BaseResponse<HotDealDto> createHotDeal(@RequestBody HotDealDto hotDealDto) {
        return BaseResponse.onSuccess(hotDealService.createHotDeal(hotDealDto), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 수정", description = "핫딜을 수정합니다.")
    @PutMapping
    public BaseResponse<HotDealDto> updateHotDeal(@RequestBody HotDealDto hotDealDto) {
        return BaseResponse.onSuccess(hotDealService.updateHotDeal(hotDealDto), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 삭제", description = "핫딜을 삭제합니다.")
    @DeleteMapping
    public BaseResponse<Long> deleteHotDeal(@RequestParam Long hotDealId) {
        return BaseResponse.onSuccess(hotDealService.deleteHotDeal(hotDealId), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 전체 조회", description = "핫딜을 전체 조회합니다.")
    @GetMapping
    public BaseResponse<Page<HotDealDto>> searchAllHotDeal(Pageable pageable) {
        return BaseResponse.onSuccess(hotDealService.searchAllHotDeal(pageable), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 카테고리별 조회", description = "카테고리별로 핫딜을 조회합니다.")
    @GetMapping("/category")
    public BaseResponse<Page<HotDealDto>> searchCategoryHotDeal(@RequestParam Long hotDealCategoryId, Pageable pageable) {
        return BaseResponse.onSuccess(hotDealService.searchCategoryHotDeal(hotDealCategoryId, pageable), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 기록 등록", description = "핫딜 기록을 등록합니다.")
    @PostMapping("/record")
    public BaseResponse<HotDealRecordDto> createHotDealRecord(@RequestBody HotDealRecordDto hotDealRecordDto) {
        return BaseResponse.onSuccess(hotDealService.createHotDealRecord(hotDealRecordDto), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 기록 수정", description = "핫딜 기록을 수정합니다.")
    @PutMapping("/record")
    public BaseResponse<HotDealRecordDto> updateHotDealRecord(@RequestBody HotDealRecordDto hotDealRecordDto) {
        return BaseResponse.onSuccess(hotDealService.updateHotDealRecord(hotDealRecordDto), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 기록 삭제", description = "핫딜 기록을 삭제합니다.")
    @DeleteMapping("/record")
    public BaseResponse<Long> deleteHotDealRecord(@RequestParam Long hotDealRecordId) {
        return BaseResponse.onSuccess(hotDealService.deleteHotDealRecord(hotDealRecordId), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 기록 검색", description = "핫딜 기록을 검색합니다.")
    @GetMapping("/record")
    public BaseResponse<Page<HotDealRecordDto>> searchHotDealRecord(@RequestParam Long hotDealId, Pageable pageable) {
        return BaseResponse.onSuccess(hotDealService.searchHotDealRecord(hotDealId, pageable), ResponseCode.OK);

    }

}
