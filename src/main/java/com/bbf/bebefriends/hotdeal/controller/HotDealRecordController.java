package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.dto.HotDealRecordDto;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import com.bbf.bebefriends.hotdeal.service.HotDealRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bbf.bebefriends.member.entity.UserRole.ADMIN;

@Tag(name = "핫딜 기록", description = "핫딜 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotdeal/record")
public class HotDealRecordController {

    private final HotDealRecordService hotDealRecordService;

    @Operation(summary = "핫딜 기록 조회", description = "핫딜 상품의 기록을 조회 합니다.")
    @GetMapping
    public BaseResponse<List<HotDealRecordDto.HotDealRecordResponse>> getHotDealRecordTopList(@RequestParam Long hotDealPostId) {
        return BaseResponse.onSuccess(hotDealRecordService.getHotDealRecordTopList(hotDealPostId), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 기록 상세 조회", description = "핫딜 상품의 상세 기록을 조회 합니다.")
    @GetMapping("/{hotDealRecordId}")
    public BaseResponse<HotDealRecordDto.HotDealRecordDetailResponse> getHotDealRecord(@PathVariable Long hotDealRecordId) {
        return BaseResponse.onSuccess(hotDealRecordService.getHotDealRecordDetail(hotDealRecordId), ResponseCode.OK);
    }

    @Operation(summary = "핫딜 기록 생성", description = "핫딜 기록을 생성합니다.")
    @PostMapping
    public BaseResponse<Void> createHotDealRecord(
            @RequestBody HotDealRecordDto.HotDealRecordRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        hotDealRecordService.createHotDealRecord(request);
        return BaseResponse.onSuccess(null, ResponseCode.CREATED);
    }

    @Operation(summary = "핫딜 기록 업데이트", description = "핫딜 기록 정보를 업데이트합니다.")
    @PutMapping("/{hotDealRecordId}")
    public BaseResponse<Void> updateHotDealRecord(
            @PathVariable Long hotDealRecordId,
            @RequestBody HotDealRecordDto.HotDealRecordUpdateRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        hotDealRecordService.updateHotDealRecord(hotDealRecordId, request);
        return BaseResponse.onSuccess(null, ResponseCode.NO_CONTENT);
    }

    @Operation(summary = "핫딜 기록 삭제", description = "핫딜 기록 정보를 삭제합니다.")
    @DeleteMapping("/{hotDealRecordId}")
    public BaseResponse<Void> deleteHotDealRecord(
            @PathVariable Long hotDealRecordId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (!userDetails.getRole().equals(ADMIN.name())) {
            throw new HotDealControllerAdvice(ResponseCode._UNAUTHORIZED);
        }
        hotDealRecordService.deleteHotDealRecord(hotDealRecordId);
        return BaseResponse.onSuccess(null, ResponseCode.NO_CONTENT);
    }

}
