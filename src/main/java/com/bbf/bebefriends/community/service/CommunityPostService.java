package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.member.entity.User;

public interface CommunityPostService {
    CommunityPostDTO.CreatePostResponse createPost(CommunityPostDTO.CreatePostRequest request, User user);
    CommunityPostDTO.UpdatePostResponse updatePost(CommunityPostDTO.UpdatePostRequest request, User user);
    String deletePost(CommunityPostDTO.DeletePostRequest request, User user);

    CommunityPostDTO.PostDetailsResponse getPostDetail(Long postId);

    // TODO: 소프트 삭제된 게시물 삭제처리
}
