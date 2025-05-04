package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.service.CommunityPostService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class CommunityPostController {
    private final CommunityPostService communityPostService;

    // 게시글 작성
    @PostMapping("/post")
    public BaseResponse<CommunityPostDTO.CreatePostResponse> createCommunityPost(@Valid @RequestBody CommunityPostDTO.CreatePostRequest request,
                                                                                 @AuthenticationPrincipal UserDetailsImpl user) {

        return BaseResponse.onSuccess(communityPostService.createPost(request, user.getUser()), ResponseCode.OK);
    }

    // 게시글 수정
    @PatchMapping("/post")
    public BaseResponse<CommunityPostDTO.UpdatePostResponse> updatePost(@Valid @RequestBody CommunityPostDTO.UpdatePostRequest request,
                                                                          @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostService.updatePost(request, user.getUser()), ResponseCode.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/post")
    public BaseResponse<String> deletePost(@Valid @RequestBody CommunityPostDTO.DeletePostRequest request,
                                             @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostService.deletePost(request, user.getUser()), ResponseCode.OK);
    }

    // 게시글 상세 페이지
    @GetMapping("/post-detail")
    public BaseResponse<CommunityPostDTO.PostDetailsResponse>  getPostDetails(@AuthenticationPrincipal UserDetailsImpl user,
                                                                              @RequestParam Long postId) {
        return BaseResponse.onSuccess(communityPostService.getPostDetail(postId), ResponseCode.OK);
    }
}
