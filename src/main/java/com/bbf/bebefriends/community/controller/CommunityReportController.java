package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityPostReportDTO;
import com.bbf.bebefriends.community.service.CommunityReportService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
@Tag(name = "커뮤니티 게시글 신고 api", description = "게시물 신고 관련 API")
public class CommunityReportController {
    private final CommunityReportService communityReportService;

    // 게시물 신고
    @PostMapping("/post/report")
    @Operation(summary = "커뮤니티 게시글 신고",
            description = "게시물을 신고합니다.\n" +
                          "게시글 id, 신고 사유, 신고 내용\n")
    public BaseResponse<String> reportPost(@AuthenticationPrincipal UserDetailsImpl user,
                                           @RequestBody CommunityPostReportDTO.PostReportRequest request) {
        return BaseResponse.onSuccess(communityReportService.reportPost(user.getUser(), request), ResponseCode.OK);
    }
}
