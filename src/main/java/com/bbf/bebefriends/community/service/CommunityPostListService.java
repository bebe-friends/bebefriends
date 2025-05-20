package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.member.entity.User;

import java.util.List;

public interface CommunityPostListService {
    // 전체 게시물 조회
    List<CommunityPostDTO.PostListResponse> getAllPosts();

    // 카테고리 별 게시물 목록 조회
    List<CommunityPostDTO.PostListResponse> getPostsByCategory(String category);
    // 게시물 검색
    List<CommunityPostDTO.PostListResponse> getPostsBySearch(String query);

    // 제목 별 게시물 목록 조회
//    List<CommunityPostDTO.PostListResponse> getPostsByTitle(String keyword);

    // 글쓴이 별 게시물 목록 조회
//    List<CommunityPostDTO.PostListResponse> getPostsByAuthor(String keyword);

    // 내가 작성한 게시물 목록 조회
    List<CommunityPostDTO.PostListResponse> getMyPosts(User user);

    // 내가 댓글단 게시물 목록 조회
    List<CommunityPostDTO.PostListResponse> getCommentedPosts(User user);

    // 내가 좋아요한 게시물 목록 조회
    List<CommunityPostDTO.PostListResponse> getLikedPosts(User user);
}
