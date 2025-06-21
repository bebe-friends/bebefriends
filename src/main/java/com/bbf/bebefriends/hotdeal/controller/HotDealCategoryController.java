package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;
import com.bbf.bebefriends.hotdeal.service.HotDealCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "핫딜 카테고리", description = "핫딜 카테고리 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/category")
public class HotDealCategoryController {

    private final HotDealCategoryService hotDealCategoryService;

    @Operation(summary = "하위 카테고리 조회", description = "부모 카테고리에 해당하는 하위 카테고리 목록을 조회합니다.")
    @GetMapping("/sub-categories")
    public BaseResponse<List<HotDealCategoryDto>> getSubCategories(@RequestParam Long parentId,
                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!userDetails.getRole().equals("ROLE_ADMIN")) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        List<HotDealCategoryDto> subCategories = hotDealCategoryService.getSubCategoriesByParentId(parentId);
        return BaseResponse.onSuccess(subCategories, ResponseCode.OK);
    }

    @Operation(summary = "대분류 카테고리 전체 조회", description = "대분류 카테고리(Depth 1) 목록을 조회합니다.")
    @GetMapping("/main-categories")
    public BaseResponse<List<HotDealCategoryDto>> getAllMainCategories(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals("ROLE_ADMIN")) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        List<HotDealCategoryDto> mainCategories = hotDealCategoryService.getAllMainCategories();
        return BaseResponse.onSuccess(mainCategories, ResponseCode.OK);
    }

}
