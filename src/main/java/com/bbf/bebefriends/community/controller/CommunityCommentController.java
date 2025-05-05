package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.service.CommunityCommentService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "커뮤니티 댓글 관련 api", description = "댓글 생성·수정·삭제 api")
public class CommunityCommentController {
    private final CommunityCommentService communityCommentService;

    @PostMapping("/comment")
    @Operation(summary = "커뮤니티 댓글 생성", description = "게시물에 댓글을 게시합니다.")
    public BaseResponse<String> createComment(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody CommunityCommentDTO.CreateCommentRequest request) {
        return BaseResponse.onSuccess(communityCommentService.createComment(user.getUser(), request), ResponseCode.OK);
    }

    @PatchMapping("/comment")
    @Operation(summary = "커뮤니티 댓글 수정", description = "본인이 생성한 댓글을 수정합니다.")
    public BaseResponse<String> updateComment(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody CommunityCommentDTO.UpdateCommentRequest request) {
        return BaseResponse.onSuccess(communityCommentService.updateComment(user.getUser(), request), ResponseCode.OK);
    }

    @DeleteMapping("/comment")
    @Operation(summary = "커뮤니티 댓글 삭제", description = "본인이 생성한 댓글을 삭제합니다.")
    public BaseResponse<String> deleteComment(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody CommunityCommentDTO.DeleteCommentRequest request) {
        return BaseResponse.onSuccess(communityCommentService.deleteComment(user.getUser(), request), ResponseCode.OK);
    }
}
