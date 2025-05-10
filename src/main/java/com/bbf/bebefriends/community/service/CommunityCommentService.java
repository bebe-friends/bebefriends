package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.member.entity.User;

import java.util.List;

public interface CommunityCommentService {
    List<CommunityCommentDTO.CommentDetails> getCommentsByPost(CommunityPost post);

    String createComment(User user, CommunityCommentDTO.CreateCommentRequest request);

    String updateComment(User user, CommunityCommentDTO.UpdateCommentRequest request);

    String deleteComment(User user, CommunityCommentDTO.DeleteCommentRequest request);
}
