package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityPostLikeDTO;
import com.bbf.bebefriends.community.entity.CommunityPostLike;
import com.bbf.bebefriends.community.service.CommunityPostLikeService;
import com.bbf.bebefriends.community.service.CommunityPostListService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "게시글 좋아요 api", description = "게시물 좋아요·취소 api")
public class CommunityPostLikeController {
    private final CommunityPostLikeService communityPostLikeService;

    // 게시물 좋아요
    @PostMapping("/post-like/{postId}")
    public BaseResponse<String> createPostLike(@PathVariable Long postId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return BaseResponse.onSuccess(communityPostLikeService.updatePostLike(postId, userDetails.getUser()), ResponseCode.OK);
    }

//    // 게시물 좋아요 취소
//    @DeleteMapping("/post-like/{postId}")
//    public BaseResponse<String> deletePostLike(@PathVariable Long postId,
//                                               @RequestBody CommunityPostLikeDTO.PostLikeRequest request) {
//        return BaseResponse.onSuccess(communityPostLikeService.deletePostLike(postId, request), ResponseCode.OK);
//    }
}
