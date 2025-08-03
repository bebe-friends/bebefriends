package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityPostLikeDTO;
import com.bbf.bebefriends.global.entity.UserDetailsImpl;
import com.bbf.bebefriends.member.entity.User;

public interface CommunityPostLikeService {
    String updatePostLike(Long postId, User user);
//    String deletePostLike(Long postId, CommunityPostLikeDTO.PostLikeRequest request);
}
