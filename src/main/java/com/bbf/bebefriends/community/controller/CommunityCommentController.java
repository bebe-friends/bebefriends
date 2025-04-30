package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.service.CommunityCommentService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommunityCommentController {
    private final CommunityCommentService communityCommentService;

    @PostMapping("/comment")
    public BaseResponse<String> createComment(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody CommunityCommentDTO.CreateCommentRequest request) {
        return BaseResponse.onSuccess(communityCommentService.createComment(user.getUser(), request), ResponseCode.OK);
    }

    @PatchMapping("/comment")
    public BaseResponse<String> updateComment(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody CommunityCommentDTO.UpdateCommentRequest request) {
        return BaseResponse.onSuccess(communityCommentService.updateComment(user.getUser(), request), ResponseCode.OK);
    }

    @DeleteMapping("/comment")
    public BaseResponse<String> deleteComment(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody CommunityCommentDTO.DeleteCommentRequest request) {
        return BaseResponse.onSuccess(communityCommentService.deleteComment(user.getUser(), request), ResponseCode.OK);
    }
}
