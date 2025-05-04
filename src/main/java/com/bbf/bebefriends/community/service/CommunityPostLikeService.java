package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityPostLikeDTO;

public interface CommunityPostLikeService {
    String createPostLike(Long postId, CommunityPostLikeDTO.PostLikeRequest request);
    String deletePostLike(Long postId, CommunityPostLikeDTO.PostLikeRequest request);
}
