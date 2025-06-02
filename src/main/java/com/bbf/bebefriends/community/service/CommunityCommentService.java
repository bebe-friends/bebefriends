package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.service.impl.CommunityCommentServiceImpl;
import com.bbf.bebefriends.member.entity.User;

import java.util.List;
import java.util.Optional;

public interface CommunityCommentService {
    List<CommunityCommentDTO.CommentCursorProjection> getCommentsByPost(Long   postId,
                                                               Long   primaryOffset,
                                                               Long   subOffset,
                                                               int    limit);
    Optional<CommunityCommentDTO.CommentCursor> getNextCursor(CommunityCommentDTO.CommentCursorProjection lastRow);
    String createComment(User user, CommunityCommentDTO.CreateCommentRequest request);

    String updateComment(User user, CommunityCommentDTO.UpdateCommentRequest request);

    String deleteComment(User user, CommunityCommentDTO.DeleteCommentRequest request);
}
