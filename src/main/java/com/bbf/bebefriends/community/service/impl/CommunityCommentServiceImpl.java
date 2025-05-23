package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.repository.CommunityCommentRepository;
import com.bbf.bebefriends.community.repository.CommunityPostRepository;
import com.bbf.bebefriends.community.service.CommunityCommentService;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityCommentServiceImpl implements CommunityCommentService {
    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityPostRepository communityPostRepository;

    @Override
    public List<CommunityCommentDTO.CommentDetails> getCommentsByPost(CommunityPost post) {
        List<CommunityComment> topComments =
                communityCommentRepository.findByPostAndParentIsNullAndDeletedAtIsNull(post);

        return topComments.stream()
                .map(this::toDto)
                .toList();
    }

    // FIXME: 대댓글의 댓글은 불가능. 깊이는 최대 2까지만 설정하도록 수정
    @Override
    @Transactional
    public String createComment(User user, CommunityCommentDTO.CreateCommentRequest request) {
        CommunityPost post = communityPostRepository.findById(request.getPostId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));
        CommunityComment parent;
        if (request.getParentId() == null) {
            parent = null;
        } else {
            parent = communityCommentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMENT_NOT_FOUND));
        }
        CommunityComment comment = CommunityComment.createComment(post, user, parent, request);
        communityCommentRepository.save(comment);

        post.increaseCommentCount();
        return "댓글을 작성하였습니다.";
    }

    @Override
    @Transactional
    public String updateComment(User user, CommunityCommentDTO.UpdateCommentRequest request) {
        CommunityComment comment = communityCommentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMENT_NOT_FOUND));
        if (!(comment.getUser().getUid().equals(user.getUid()))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        comment.updateComment(request.getContent());
        return "댓글을 수정하였습니다.";
    }

    @Override
    @Transactional
    public String deleteComment(User user, CommunityCommentDTO.DeleteCommentRequest request) {
        CommunityComment comment = communityCommentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMENT_NOT_FOUND));
        if (!(comment.getUser().getUid().equals(user.getUid()))) {
            throw new CommunityControllerAdvice(ResponseCode._UNAUTHORIZED);
        }

        comment.setDeletedAt();
        CommunityPost post = comment.getPost();
        post.decreaseCommentCount();

        return "댓글을 삭제하였습니다.";
    }

    private CommunityCommentDTO.CommentDetails toDto(CommunityComment comment) {
        List<CommunityCommentDTO.CommentDetails> replies = comment.getSubComments().stream()
                .filter(c -> c.getDeletedAt() == null)
                .map(this::toDto)
                .toList();

        return CommunityCommentDTO.CommentDetails.builder()
                .commentId(comment.getId())
                .commenter(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedDate())
                .replies(replies)
                .build();
    }
}
