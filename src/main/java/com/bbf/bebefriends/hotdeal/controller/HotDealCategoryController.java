package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.service.HotDealCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bbf.bebefriends.member.entity.UserRole.ADMIN;

@Tag(name = "핫딜 카테고리", description = "핫딜 카테고리 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/category")
public class HotDealCategoryController {

    private final HotDealCategoryService hotDealCategoryService;

    @Operation(summary = "하위 카테고리 조회", description = "부모 카테고리에 해당하는 하위 카테고리 목록을 조회합니다.")
    @GetMapping("/sub-categories/{parentId}")
    public BaseResponse<List<HotDealCategoryDto.HotDealResponse>> getSubCategories(
            @PathVariable Long parentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        List<HotDealCategoryDto.HotDealResponse> subCategories = hotDealCategoryService.getSubCategoriesByParentId(parentId);
        return BaseResponse.onSuccess(subCategories, ResponseCode.OK);
    }

    @Operation(summary = "대분류 카테고리 전체 조회", description = "대분류 카테고리(Depth 1) 목록을 조회합니다.")
    @GetMapping("/main-categories")
    public BaseResponse<List<HotDealCategoryDto.HotDealResponse>> getAllMainCategories(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        List<HotDealCategoryDto.HotDealResponse> mainCategories = hotDealCategoryService.getAllMainCategories();
        return BaseResponse.onSuccess(mainCategories, ResponseCode.OK);
    }

    @Operation(summary = "카테고리 생성", description = "중/소/세 분류 카테고리를 생성합니다.")
    @PostMapping("/create")
    public BaseResponse<Void> createOrFindCategoryWithHotDeal(
            @RequestBody HotDealCategoryDto.HotDealCategoryRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        hotDealCategoryService.createNewCategory(request);
        return BaseResponse.onSuccess(null, ResponseCode.OK);
    }

    @Operation(summary = "카테고리 매칭", description = "세분류 카테고리를 찾고, 해당 카테고리를 핫딜에 매칭합니다.")
    @PostMapping("/detailed-category/match")
    public BaseResponse<Void> matchCategoryWithHotDeal(
            @RequestBody HotDealCategoryDto.CategoryRequest categoryRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        hotDealCategoryService.matchCategoriesWithHotDeal(categoryRequest);
        return BaseResponse.onSuccess(null, ResponseCode.OK);
    }

}
