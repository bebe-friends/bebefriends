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
@Tag(name = "커뮤니티 게시글 신고 api", description = "게시물 신고 관련 API. 신고 사유(reason): " +
                                                "SPAM(\"스팸 홍보/도배글 입니다.\")," +
                                                "PORN(\"음란물입니다.\")," +
                                                "ILLEGAL_CONTENT(\"불법정보를 포함하고 있습니다.\")," +
                                                "ABUSE_HATE(\"욕설/생명경시/혐오/차별적 표현입니다.\")," +
                                                "PERSONAL_INFO(\"개인정보 노출 게시물입니다.\")," +
                                                "OFFENSIVE(\"불쾌한 표현이 있습니다.\")," +
                                                "OTHER(\"기타\")")
public class CommunityReportController {
    private final CommunityReportService communityReportService;

    // 게시물 신고
    @PostMapping("/post/report")
    @Operation(summary = "커뮤니티 게시글 신고",
               description = "게시물을 신고합니다.\n" +
                             "게시글 id, 신고 사유, 신고 내용")
    public BaseResponse<String> reportPost(@AuthenticationPrincipal UserDetailsImpl user,
                                           @RequestBody CommunityPostReportDTO.PostReportRequest request) {
        return BaseResponse.onSuccess(communityReportService.reportPost(user.getUser(), request), ResponseCode.OK);
    }

    // 게시물 차단
//    @PostMapping("/post/block")
//    @Operation(summary = "커뮤니티 게시글 차단", description = "게시물을 차단합니다.")
//    public BaseResponse<String> blockPost(@AuthenticationPrincipal UserDetailsImpl user,
//                                          @RequestParam Long postId) {
//        return BaseResponse.onSuccess(communityReportService.blockPost(user.getUser(), postId), ResponseCode.OK);
//    }

    // 게시물 차단 해제
    @PostMapping("/post/unblock")
    @Operation(summary = "커뮤니티 게시글 차단 해제", description = "게시물을 차단 해제합니다.")
    public BaseResponse<String> unblockPost(@AuthenticationPrincipal UserDetailsImpl user,
                                          @RequestParam Long postId) {
        return BaseResponse.onSuccess(communityReportService.unblockPost(user.getUser(), postId), ResponseCode.OK);
    }

    // 댓글 신고 및 차단
    @PostMapping("/comment/report")
    @Operation(summary = "커뮤니티 댓글 신고", description = "댓글을 신고 및 차단합니다.\n" +
                                                         "댓글 id, 신고 사유, 신고 내용")
    public BaseResponse<String> reportComment(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody CommunityPostReportDTO.CommentReportRequest request) {
        return BaseResponse.onSuccess(communityReportService.reportComment(user.getUser(), request), ResponseCode.OK);
    }

    // 댓글 차단 해제
    @PostMapping("/comment/unblock")
    @Operation(summary = "커뮤니티 댓글 차단 해제", description = "댓글을 차단 해제합니다.")
    public BaseResponse<String> unblockComment(@AuthenticationPrincipal UserDetailsImpl user,
                                               @RequestParam Long commentId) {
        return BaseResponse.onSuccess(communityReportService.unblockComment(user.getUser(), commentId), ResponseCode.OK);
    }

    // 유저 차단
    @PostMapping("/user/block")
    @Operation(summary = "커뮤니티 유저 차단", description = "유저를 차단합니다.")
    public BaseResponse<String> blockUser(@AuthenticationPrincipal UserDetailsImpl user,
                                          @RequestParam Long targetUserId) {
        return BaseResponse.onSuccess(communityReportService.blockUser(user.getUser(), targetUserId), ResponseCode.OK);
    }

    // 유저 차단 해제
    @PostMapping("/user/unblock")
    @Operation(summary = "커뮤니티 유저 차단 해제", description = "유저를 차단 해제합니다.")
    public BaseResponse<String> unblockUser(@AuthenticationPrincipal UserDetailsImpl user,
                                            @RequestParam Long targetUserId) {
        return BaseResponse.onSuccess(communityReportService.unblockUser(user.getUser(), targetUserId), ResponseCode.OK);
    }
}
