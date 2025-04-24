package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.service.CommunityPostListService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommunityPostListController {
    private final CommunityPostListService communityPostListService;

    // 모든 게시물
    @GetMapping("/all-posts")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getPosts() {
        return BaseResponse.onSuccess(communityPostListService.getAllPosts(), ResponseCode.OK);
    }

    // 카테고리 별 조회
    @GetMapping("/category-posts")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getCategoryPosts(@RequestParam String category) {
        return BaseResponse.onSuccess(communityPostListService.getPostsByCategory(category), ResponseCode.OK);
    }

    // 제목 조회
    @GetMapping("/search/title")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getTitleSearch(@RequestParam String title) {
        return BaseResponse.onSuccess(communityPostListService.getPostsByTitle(title), ResponseCode.OK);
    }

    // 작성자 조회
    @GetMapping("/search/author")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getAuthorSearch(@RequestParam String author) {
        return BaseResponse.onSuccess(communityPostListService.getPostsByAuthor(author), ResponseCode.OK);
    }

    // 내가 쓴 게시물
    @GetMapping("/my-posts")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getMyPosts(@AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostListService.getMyPosts(user.getUser()), ResponseCode.OK);
    }

    // 댓글 단 게시물
    @GetMapping("/commented-posts")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getCommentedPosts(@AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostListService.getCommentedPosts(user.getUser()), ResponseCode.OK);
    }

    // 좋아요한 게시물
    @GetMapping("/liked-posts")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getLikedPosts(@AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostListService.getLikedPosts(user.getUser()), ResponseCode.OK);
    }
}
