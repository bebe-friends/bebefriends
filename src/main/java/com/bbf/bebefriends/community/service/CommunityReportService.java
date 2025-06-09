package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityPostReportDTO;
import com.bbf.bebefriends.member.entity.User;

public interface CommunityReportService {
    String reportPost(User user, CommunityPostReportDTO.PostReportRequest request);
}
