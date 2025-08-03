package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.global.entity.BasePageResponse;
import com.bbf.bebefriends.global.entity.BaseResponse;
import com.bbf.bebefriends.member.entity.User;

public interface CommunityCommentService {
    CommunityCommentDTO.ParentOnlyResponse getParentOnly(User user, Long commentId);
    BasePageResponse<CommunityCommentDTO.ParentCommentResponse> getParentComments(User user, Long postId, Long parentPage, int parentSize);
    BasePageResponse<CommunityCommentDTO.ChildCommentDTO> getChildComments(User user, Long parentId, Long cursorId, int pageSize);
    CommunityCommentDTO.CreateCommentResponse createComment(User user, CommunityCommentDTO.CreateCommentRequest request);

    String updateComment(User user, CommunityCommentDTO.UpdateCommentRequest request);

    String deleteComment(User user, CommunityCommentDTO.DeleteCommentRequest request);
//    List<CommunityCommentDTO.ParentCommentResponse> getCommentsByPost(Long postId, Long primaryOffset, Long subOffset, int limit);
//    Optional<CommunityCommentDTO.CommentCursor> getNextCursor(CommunityCommentDTO.CommentCursorProjection lastRow);
}
