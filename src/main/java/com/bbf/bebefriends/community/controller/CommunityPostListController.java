package com.bbf.bebefriends.community.controller;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.service.CommunityPostListService;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.User;
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
@RequestMapping("/api/v1/posts")
@Tag(name = "게시물 목록 api", description = "모든 게시물·카테고리·제목·작성자·내가 쓴 게시물·댓글 단 게시물·좋아요한 게시물 목록 api")
public class CommunityPostListController {
    private final CommunityPostListService communityPostListService;

    /**
     * GET /posts
     *   ?type=MY|COMMENTED|LIKED
     *   ?category=<카테고리명>
     *   ?keyword=<검색어>

     * - keyword가 있으면 제목/글쓴이 검색
     * - else if category가 있으면 카테고리 조회
     * - else if type이 있으면 내가 쓴/댓글 단/좋아요한 조회
     * - 그 외에는 전체 조회
     */
    @GetMapping
    @Operation(
            summary = "게시물 조회",
            description = """
                          type, category, keyword 조합에 따라 전체/내가 쓴/댓글 단/좋아요한/카테고리별/검색 조회\n
                          첫 조회 시 cursorId 는 제외\n
                          기본 pageSize 는 20
                          """
    )
    public BaseResponse<CommunityPostDTO.PostListWithCursorResponse> getPosts(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(value = "cursorId", required = false) Long cursorId,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "type",     required = false) PostType   type,
            @RequestParam(value = "category", required = false) String     category,
            @RequestParam(value = "keyword",  required = false) String     keyword
    ) {
        User currentUser = user.getUser();

        CommunityPostDTO.PostListWithCursorResponse response;

        if (keyword != null && !keyword.isBlank()) {
            // 검색어가 있으면 검색 서비스 호출
            response = communityPostListService.getPostsBySearch(keyword, currentUser, cursorId, pageSize);
        }
        else if (category != null && !category.isBlank()) {
            // 카테고리 파라미터가 있으면 카테고리 조회 서비스 호출
            response = communityPostListService.getPostsByCategory(category, currentUser, cursorId, pageSize);
        }
        else if (type != null) {
            // type 파라미터가 있으면 내가 쓴/댓글 단/좋아요한 조회
            switch (type) {
                case MY        -> response = communityPostListService.getMyPosts(currentUser, cursorId, pageSize);
                case COMMENTED -> response = communityPostListService.getCommentedPosts(currentUser, cursorId, pageSize);
                case LIKED     -> response = communityPostListService.getLikedPosts(currentUser, cursorId, pageSize);
                default        -> throw new CommunityControllerAdvice(ResponseCode._BAD_REQUEST);
            }
        }
        else {
            // 파라미터가 아무것도 없으면 전체 게시물 조회
            response = communityPostListService.getAllPosts(currentUser, cursorId, pageSize);
        }

        return BaseResponse.onSuccess(response, ResponseCode.OK);
    }

    // 조회 타입 구분용 enum
    public enum PostType {
        MY,
        COMMENTED,
        LIKED
    }
}
