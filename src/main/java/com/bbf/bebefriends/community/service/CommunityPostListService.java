package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.member.entity.User;

import java.util.List;

public interface CommunityPostListService {
    // 전체 게시물 조회
    CommunityPostDTO.PostListWithCursorResponse getAllPosts(User user, Long cursorId, int pageSize);

    // 카테고리 별 게시물 목록 조회
    CommunityPostDTO.PostListWithCursorResponse getPostsByCategory(String category, User user, Long cursorId, int pageSize);
    // 게시물 검색
    CommunityPostDTO.PostListWithCursorResponse getPostsBySearch(String query, User user, Long cursorId, int pageSize);

    // 내가 작성한 게시물 목록 조회
    CommunityPostDTO.PostListWithCursorResponse getMyPosts(User user, Long cursorId, int pageSize);

    // 내가 댓글단 게시물 목록 조회
    CommunityPostDTO.PostListWithCursorResponse getCommentedPosts(User user, Long cursorId, int pageSize);

    // 내가 좋아요한 게시물 목록 조회
    CommunityPostDTO.PostListWithCursorResponse getLikedPosts(User user, Long cursorId, int pageSize);
}
