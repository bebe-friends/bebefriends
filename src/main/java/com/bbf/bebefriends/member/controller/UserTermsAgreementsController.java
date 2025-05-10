package com.bbf.bebefriends.member.controller;

import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.service.UserTermsAgreementsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/terms-agreements")
@RequiredArgsConstructor
@Tag(name = "User Terms Agreements", description = "사용자 약관 동의 API")
public class UserTermsAgreementsController {

    private final UserTermsAgreementsService termsAgreementsService;

    /**
     * 약관 동의 정보 조회
     *
     * @param uid 사용자 고유 ID
     * @return 약관 동의 정보
     */
    @GetMapping("/{uid}")
    @Operation(summary = "약관 동의 정보 조회", description = "특정 사용자의 약관 동의 정보를 조회합니다.")
    public BaseResponse<UserDTO.UserTermsAgreementsResponse> getTermsAgreements(@PathVariable String uid) {
        return BaseResponse.onSuccess(termsAgreementsService.getTermsAgreements(uid), ResponseCode.OK);
    }

    /**
     * 약관 동의 정보 업데이트
     *
     * @param request 업데이트 요청 데이터
     */
    @PutMapping("/update")
    @Operation(summary = "약관 동의 정보 업데이트", description = "특정 사용자의 약관 동의 정보를 업데이트합니다.")
    public void updateTermsAgreements(@RequestBody UserDTO.UpdateTermsAgreementsRequest request) {
        termsAgreementsService.updateTermsAgreements(
                request.uid(),
                request.agreement(),
                request.privatePolicy(),
                request.age()
        );
    }
}