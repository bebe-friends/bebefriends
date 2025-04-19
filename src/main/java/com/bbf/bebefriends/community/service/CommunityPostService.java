package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.member.entity.Member;

public interface CommunityPostService {
    CommunityPostDTO.CreatePostResponse createPost(CommunityPostDTO.CreatePostRequest request, Member member);
    CommunityPostDTO.UpdatePostResponse updatePost(CommunityPostDTO.UpdatePostRequest request, Member member);
    String deletePost(CommunityPostDTO.DeletePostRequest request, Member member);
}
