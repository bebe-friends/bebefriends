package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.repository.CommunityCommentRepository;
import com.bbf.bebefriends.community.service.CommunityCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityCommentServiceImpl implements CommunityCommentService {
    private final CommunityCommentRepository communityCommentRepository;

    @Override
    public List<CommunityCommentDTO.CommentDetails> getCommentsByPost(CommunityPost post) {
        List<CommunityComment> topComments =
                communityCommentRepository.findByPostAndParentIsNullAndDeletedAtIsNull(post);

        return topComments.stream()
                .map(this::toDto)
                .toList();
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
