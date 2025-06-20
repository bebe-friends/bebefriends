package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealRecordDto;
import com.bbf.bebefriends.hotdeal.service.HotDealRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "핫딜 기록", description = "핫딜 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/record")
public class HotDealRecordController {

    private final HotDealRecordService hotDealRecordService;

    @Operation(summary = "핫딜 기록 조회", description = "핫딜 상품의 기록을 조회 합니다.")
    @GetMapping
    public BaseResponse<List<HotDealRecordDto.HotDealRecordResponse>> likeHotDealPost(@RequestParam Long hotDealPostId) {
        return BaseResponse.onSuccess(hotDealRecordService.getHotDealRecordTopList(hotDealPostId), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 기록 생성", description = "핫딜 기록을 생성합니다.")
    @PostMapping
    public BaseResponse<Void> createHotDealRecord(@RequestBody HotDealRecordDto.HotDealRecordRequest request) {
        hotDealRecordService.createHotDealRecord(request);
        return BaseResponse.onSuccess(null, ResponseCode.CREATED);
    }

}
