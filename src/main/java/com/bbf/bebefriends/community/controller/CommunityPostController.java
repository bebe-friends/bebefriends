package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.service.CommunityPostService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "커뮤니티 게시글 api", description = "게시물 생성·수정·삭제·상세 페이지 관련 API")
public class CommunityPostController {
    private final CommunityPostService communityPostService;

    // 게시글 작성
    @PostMapping("/post")
    @Operation(summary = "커뮤니티 게시글 생성", description = "게시물을 게시합니다.")
    public BaseResponse<CommunityPostDTO.CreatePostResponse> createCommunityPost(@Valid @RequestBody CommunityPostDTO.CreatePostRequest request,
                                                                                 @AuthenticationPrincipal UserDetailsImpl user) {

        return BaseResponse.onSuccess(communityPostService.createPost(request, user.getUser()), ResponseCode.OK);
    }

    // 게시글 수정
    @PatchMapping("/post")
    @Operation(summary = "커뮤니티 게시글 수정", description = "본인이 작성한 게시물을 수정합니다.")
    public BaseResponse<CommunityPostDTO.UpdatePostResponse> updatePost(@Valid @RequestBody CommunityPostDTO.UpdatePostRequest request,
                                                                          @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostService.updatePost(request, user.getUser()), ResponseCode.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/post")
    @Operation(summary = "커뮤니티 게시글 삭제", description = "본인이 작성한 게시물을 삭제합니다.")
    public BaseResponse<String> deletePost(@Valid @RequestBody CommunityPostDTO.DeletePostRequest request,
                                             @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostService.deletePost(request, user.getUser()), ResponseCode.OK);
    }

    // 게시글 상세 페이지
    @GetMapping("/post-detail")
    @Operation(summary = "커뮤니티 게시글 상세 페이자", description = "게시글 상세 페이지를 조회합니다.")
    public BaseResponse<CommunityPostDTO.PostDetailsResponse>  getPostDetails(@AuthenticationPrincipal UserDetailsImpl user,
                                                                              @RequestParam Long postId) {
        return BaseResponse.onSuccess(communityPostService.getPostDetail(postId), ResponseCode.OK);
    }
}
