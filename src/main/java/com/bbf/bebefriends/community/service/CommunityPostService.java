package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommunityPostService {
    CommunityPostDTO.CreatePostResponse createPost(CommunityPostDTO.CreatePostRequest request, List<MultipartFile> images, User user);
    CommunityPostDTO.UpdatePostResponse updatePost(CommunityPostDTO.UpdatePostRequest request, List<MultipartFile> newImages, User user);
    String deletePost(CommunityPostDTO.DeletePostRequest request, User user);
    CommunityPostDTO.PostDetailsResponse getPostDetail(User user, Long postId);

    // TODO: 소프트 삭제된 게시물 삭제처리
}
