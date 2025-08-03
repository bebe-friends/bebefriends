package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.service.HotDealSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "핫딜 상품 검색", description = "핫딜 상품 검색 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/search")
public class HotDealSearchController {

    private final HotDealSearchService hotDealSearchService;

    @Operation(summary = "핫딜 상품 전체 조회", description = "모든 핫딜 상품을 페이징하여 조회합니다.")
    @GetMapping
    public BaseResponse<List<HotDealDto.HotDealSearchResponse>> getAllHotDeals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<HotDealDto.HotDealSearchResponse> results = hotDealSearchService.getAllHotDeals(page, size);
        return BaseResponse.onSuccess(results, ResponseCode.OK);
    }

    @Operation(summary = "대분류 카테고리별 핫딜 조회", description = "대분류 카테고리(depth=1)에 속한 핫딜 상품들을 조회합니다.")
    @GetMapping("/category/{categoryId}")
    public BaseResponse<List<HotDealDto.HotDealSearchResponse>> getHotDealsByMainCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<HotDealDto.HotDealSearchResponse> results =
                hotDealSearchService.getHotDealsByMainCategory(categoryId, page, size);
        return BaseResponse.onSuccess(results, ResponseCode.OK);
    }

    @Operation(summary = "핫딜 상품 이름으로 검색", description = "핫딜 상품 이름으로 검색합니다.")
    @GetMapping("/name")
    public BaseResponse<List<HotDealDto.HotDealSearchResponse>> searchHotDealsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<HotDealDto.HotDealSearchResponse> results = hotDealSearchService.searchByName(name, page, size);
        return BaseResponse.onSuccess(results, ResponseCode.OK);
    }

    @Operation(summary = "핫딜 검색 (카테고리 이름)", description = "세분류 카테고리 이름으로 핫딜을 검색합니다.")
    @GetMapping("/category/{categoryName}")
    public BaseResponse<List<HotDealDto.HotDealSearchResponse>> searchHotDeals(
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<HotDealDto.HotDealSearchResponse> results = hotDealSearchService.searchByDetailCategory(
                categoryName, page, size);
        return BaseResponse.onSuccess(results, ResponseCode.OK);
    }
}
