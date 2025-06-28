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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.bbf.bebefriends.member.entity.UserRole.ADMIN;

@Tag(name = "핫딜 상품", description = "핫딜 상품 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal")
public class HotDealController {

    private final HotDealService hotDealService;

    @Operation(summary = "핫딜 상품 상태 변경", description = "등록되어 있는 핫딜 상품의 상태를 변경 합니다.")
    @PostMapping("/status")
    public BaseResponse<Void> changeHotDealStatus(@RequestBody HotDealDto.HotDealStatusRequest request,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        hotDealService.updateHotDealStatus(userDetails.getUser(), request);
        return BaseResponse.onSuccess(null, ResponseCode.NO_CONTENT);
    }

    @Operation(summary = "핫딜 상품 등록", description = "핫딜 상품을 등록합니다.")
    @PostMapping("/create")
    public BaseResponse<Void> createHotDeal(@RequestBody HotDealDto.HotDealRequest request,
                                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        hotDealService.createHotDeal(userDetails.getUser(), request, images);
        return BaseResponse.onSuccess(null, ResponseCode.CREATED);
    }

}
