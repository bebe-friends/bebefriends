package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityPostReportDTO;
import com.bbf.bebefriends.member.entity.User;

public interface CommunityReportService {
    String reportPost(User user, CommunityPostReportDTO.PostReportRequest request);
    String blockPost(User user, Long postId);
    String unblockPost(User user, Long postId);
    String reportComment(User user, CommunityPostReportDTO.CommentReportRequest request);
    String unblockComment(User user, Long commentId);
    String blockUser(User user, Long userId);
    String unblockUser(User user, Long userId);
}
