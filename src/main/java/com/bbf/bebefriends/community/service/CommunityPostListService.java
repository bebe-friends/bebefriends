package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.global.entity.BasePageResponse;
import com.bbf.bebefriends.member.entity.User;

public interface CommunityPostListService {
    // 전체 게시물 조회
    BasePageResponse<CommunityPostDTO.PostListResponse> getAllPosts(User user, Long cursorId, int pageSize);

    // 카테고리 별 게시물 목록 조회
    BasePageResponse<CommunityPostDTO.PostListResponse> getPostsByCategory(Long categoryId, User user, Long cursorId, int pageSize);
    // 게시물 검색
    BasePageResponse<CommunityPostDTO.PostListResponse> getPostsBySearch(String query, User user, Long cursorId, int pageSize);

    // 내가 작성한 게시물 목록 조회
    BasePageResponse<CommunityPostDTO.PostListResponse> getMyPosts(User user, Long cursorId, int pageSize);

    // 내가 댓글단 게시물 목록 조회
    BasePageResponse<CommunityPostDTO.PostListResponse> getCommentedPosts(User user, Long cursorId, int pageSize);

    // 내가 좋아요한 게시물 목록 조회
    BasePageResponse<CommunityPostDTO.PostListResponse> getLikedPosts(User user, Long cursorId, int pageSize);
}
