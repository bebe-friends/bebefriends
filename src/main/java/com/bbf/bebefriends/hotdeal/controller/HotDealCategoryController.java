package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;
import com.bbf.bebefriends.hotdeal.service.HotDealCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "핫딜 카테고리", description = "핫딜 카테고리 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hot-deal-category")
public class HotDealCategoryController {

    private final HotDealCategoryService hotDealCategoryService;

    @Operation(summary = "핫딜 카테고리 등록", description = "핫딜 카테고리를 등록합니다.")
    @PostMapping
    public BaseResponse<HotDealCategoryDto> createHotDealCategory(@RequestBody HotDealCategoryDto hotDealCategoryDto) {
        return BaseResponse.onSuccess(hotDealCategoryService.createHotDealCategory(hotDealCategoryDto), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 카테고리 수정", description = "핫딜 카테고리를 수정합니다.")
    @PutMapping
    public BaseResponse<HotDealCategoryDto> updateHotDealCategory(@RequestBody HotDealCategoryDto hotDealCategoryDto) {
        return BaseResponse.onSuccess(hotDealCategoryService.updateHotDealCategory(hotDealCategoryDto), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 카테고리 삭제", description = "핫딜 카테고리를 삭제합니다.")
    @DeleteMapping
    public BaseResponse<Long> deleteHotDealCategory(@RequestParam Long hotDealCategoryId) {
        return BaseResponse.onSuccess(hotDealCategoryService.deleteHotDealCategory(hotDealCategoryId), ResponseCode.OK);

    }

    @Operation(summary = "핫딜 카테고리 전체 조회", description = "핫딜 카테고리를 전체 조회합니다.")
    @GetMapping
    public BaseResponse<List<HotDealCategoryDto>> searchAllHotDealCategory() {
        return BaseResponse.onSuccess(hotDealCategoryService.searchAllHotDealCategory(), ResponseCode.OK);

    }

}
