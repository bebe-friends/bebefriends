package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.service.CommunityPostService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
@Tag(name = "커뮤니티 게시글 api", description = "게시물 생성·수정·삭제·상세 페이지 관련 API")
public class CommunityPostController {
    private final CommunityPostService communityPostService;

    // 게시글 작성
    @PostMapping(
            value = "/post",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "커뮤니티 게시글 생성", description = "게시물을 게시합니다.")
    public BaseResponse<CommunityPostDTO.CreatePostResponse> createCommunityPost(
            @RequestPart("data") @Valid CommunityPostDTO.CreatePostRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        return BaseResponse.onSuccess(communityPostService.createPost(request, images, user.getUser()), ResponseCode.OK);
    }

    // 게시글 수정
    @PatchMapping(
            value = "/post",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "커뮤니티 게시글 수정", description = "본인이 작성한 게시물을 수정합니다.")
    public BaseResponse<CommunityPostDTO.UpdatePostResponse> updatePost(
            @RequestPart("data") @Valid CommunityPostDTO.UpdatePostRequest request,
            @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages,
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        return BaseResponse.onSuccess(communityPostService.updatePost(request, newImages, user.getUser()), ResponseCode.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/post")
    @Operation(summary = "커뮤니티 게시글 삭제", description = "본인이 작성한 게시물을 삭제합니다.")
    public BaseResponse<String> deletePost(@Valid @RequestBody CommunityPostDTO.DeletePostRequest request,
                                             @AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostService.deletePost(request, user.getUser()), ResponseCode.OK);
    }

    // 게시글 상세 페이지
    @GetMapping("/post/detail")
    @Operation(summary = "커뮤니티 게시글 상세 페이지", description = "게시글 상세 페이지를 조회합니다.")
    public BaseResponse<CommunityPostDTO.PostDetailsResponse>  getPostDetails(@RequestParam Long postId) {
        return BaseResponse.onSuccess(communityPostService.getPostDetail(postId), ResponseCode.OK);
    }
}
