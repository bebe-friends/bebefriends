package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.service.CommunityPostListService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "게시물 목록 api", description = "모든 게시물·카테고리·제목·작성자·내가 쓴 게시물·댓글 단 게시물·좋아요한 게시물 목록 api")
public class CommunityPostListController {
    private final CommunityPostListService communityPostListService;

    // 모든 게시물
    @GetMapping("/all-posts")
    @Operation(summary = "전체 게시물 목록", description = "전체 게시물을 조회합니다.(아무런 인증 필요 없음)")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getPosts() {
        return BaseResponse.onSuccess(communityPostListService.getAllPosts(), ResponseCode.OK);
    }

    // 카테고리 별 조회
    @GetMapping("/category-posts")
    @Operation(summary = "카테고리 별 게시물 목록", description = "카테고리 별로 게시물 목록을 조회합니다.")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getCategoryPosts(@RequestParam String category) {
        return BaseResponse.onSuccess(communityPostListService.getPostsByCategory(category), ResponseCode.OK);
    }

    // 게시물 검색(제목, 글쓴이)
    @GetMapping("/search")
    @Operation(summary = "커뮤니티 게시물 검색", description = "제목 혹은 글쓴이을 검색하여 게시물 목록을 조회합니다.")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> searchPosts(@RequestParam String query) {
        return BaseResponse.onSuccess(communityPostListService.getPostsBySearch(query), ResponseCode.OK);
    }

    // 제목 조회
//    @GetMapping("/search/title")
//    @Operation(summary = "제목 검색", description = "제목을 검색하여 게시물 목록을 조회합니다.")
//    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getTitleSearch(@RequestParam String title) {
//        return BaseResponse.onSuccess(communityPostListService.getPostsByTitle(title), ResponseCode.OK);
//    }
//
//    // 작성자 조회
//    @GetMapping("/search/author")
//    @Operation(summary = "작성자 검색", description = "작성자를 검색하여 게시물 목록을 조회합니다.")
//    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getAuthorSearch(@RequestParam String author) {
//        return BaseResponse.onSuccess(communityPostListService.getPostsByAuthor(author), ResponseCode.OK);
//    }

    // 내가 쓴 게시물
    @GetMapping("/my-posts")
    @Operation(summary = "내가 쓴 게시물", description = "내가 쓴 게시물을 조회합니다.")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getMyPosts(@AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostListService.getMyPosts(user.getUser()), ResponseCode.OK);
    }

    // 댓글 단 게시물
    @GetMapping("/commented-posts")
    @Operation(summary = "내가 댓글 단 게시물", description = "내가 댓글 단 게시물을 조회합니다.")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getCommentedPosts(@AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostListService.getCommentedPosts(user.getUser()), ResponseCode.OK);
    }

    // 좋아요한 게시물
    @GetMapping("/liked-posts")
    @Operation(summary = "내가 좋아요한 게시물", description = "내가 좋아요한 게시물을 조회합니다.")
    public BaseResponse<List<CommunityPostDTO.PostListResponse>> getLikedPosts(@AuthenticationPrincipal UserDetailsImpl user) {
        return BaseResponse.onSuccess(communityPostListService.getLikedPosts(user.getUser()), ResponseCode.OK);
    }
}
