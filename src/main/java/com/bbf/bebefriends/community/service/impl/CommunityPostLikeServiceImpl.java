package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityPostLikeDTO;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.entity.CommunityPostLike;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.repository.CommunityPostLikeRepository;
import com.bbf.bebefriends.community.repository.CommunityPostRepository;
import com.bbf.bebefriends.community.service.CommunityPostLikeService;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityPostLikeServiceImpl implements CommunityPostLikeService {
    private final CommunityPostLikeRepository communityPostLikeRepository;
    private final CommunityPostRepository communityPostRepository;
    private final UserRepository userRepository;

    // 게시물 좋아요
    @Override
    public String createPostLike(Long postId, CommunityPostLikeDTO.PostLikeRequest request) {
        CommunityPost communityPost = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));
        // FIXME: 멤버 예외처리 수정 필요
        CommunityPostLike communityPostLike = CommunityPostLike.createPostLike(userRepository.findById(request.getUserUid()).orElseThrow(), communityPost);

        communityPostLikeRepository.save(communityPostLike);
        communityPost.increaseLikeCount();

        return "게시물에 좋아요를 눌렀습니다.";
    }

    // 게시물 좋아요 취소
    @Override
    public String deletePostLike(Long postId, CommunityPostLikeDTO.PostLikeRequest request) {
        CommunityPost communityPost = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));
        // FIXME: 멤버 예외처리 수정 필요
        CommunityPostLike communityPostLike = communityPostLikeRepository.findByPostAndUser(communityPost,
                userRepository.findById(request.getUserUid()).orElseThrow())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_LIKE_NOT_FOUND));

        communityPostLikeRepository.delete(communityPostLike);
        communityPost.decreaseLikeCount();

        return "게시물에 좋아요를 취소했습니다.";
    }
}
