package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.service.HotDealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "핫딜", description = "핫딜 관련 API @유석균")
@RestController
@RequiredArgsConstructor
@RequestMapping("/hot-deal")
public class HotDealController {

    private final HotDealService hotDealService;

    @Operation(summary = "핫딜 등록", description = "핫딜을 등록합니다.")
    @PostMapping
    public BaseResponse<HotDealDto> createHotDeal(@RequestBody HotDealDto hotDealDto) {
        return BaseResponse.onSuccess(hotDealService.createHotDeal(hotDealDto), ResponseCode.OK);

    }

}
