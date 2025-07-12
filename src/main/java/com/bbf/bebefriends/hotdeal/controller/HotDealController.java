package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.service.HotDealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.bbf.bebefriends.member.entity.UserRole.ADMIN;

@Tag(name = "핫딜 상품", description = "핫딜 상품 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal")
public class HotDealController {

    private final HotDealService hotDealService;

    @Operation(summary = "핫딜 상품 등록", description = "핫딜 상품을 등록합니다.")
    @PostMapping("/create")
    public BaseResponse<Void> createHotDeal(@RequestBody HotDealDto.HotDealRequest request,
//                                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        hotDealService.createHotDeal(userDetails.getUser(), request, null);
        return BaseResponse.onSuccess(null, ResponseCode.CREATED);
    }

    @Operation(summary = "핫딜 상품 수정", description = "핫딜 상품을 수정합니다.")
    @PutMapping("/{id}")
    public BaseResponse<Void> updateHotDeal(
            @PathVariable Long id,
            @RequestBody HotDealDto.HotDealRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        hotDealService.updateHotDeal(id, userDetails.getUser(), request, null);
        return BaseResponse.onSuccess(null, ResponseCode.OK);
    }

    @Operation(summary = "핫딜 상품 삭제", description = "핫딜 상품을 삭제합니다.")
    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteHotDeal(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        hotDealService.deleteHotDeal(id, userDetails.getUser());
        return BaseResponse.onSuccess(null, ResponseCode.OK);
    }

    @Operation(summary = "핫딜 상품 상세 조회", description = "핫딜 상품의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public BaseResponse<HotDealDto.HotDealDetailResponse> getHotDealDetail(@PathVariable Long id) {
        HotDealDto.HotDealDetailResponse detail = hotDealService.getHotDealDetail(id);
        return BaseResponse.onSuccess(detail, ResponseCode.OK);
    }

}
