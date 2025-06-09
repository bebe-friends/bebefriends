package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityPostReportDTO;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.entity.CommunityPostBlock;
import com.bbf.bebefriends.community.entity.CommunityPostReport;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.repository.CommunityPostBlockRepository;
import com.bbf.bebefriends.community.repository.CommunityPostReportRepository;
import com.bbf.bebefriends.community.repository.CommunityPostRepository;
import com.bbf.bebefriends.community.service.CommunityReportService;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityReportServiceImpl implements CommunityReportService {
    private final CommunityPostBlockRepository communityPostBlockRepository;
    private final CommunityPostRepository communityPostRepository;
    private final CommunityPostReportRepository communityPostReportRepository;

    @Override
    public String reportPost(User user, CommunityPostReportDTO.PostReportRequest request) {
        CommunityPost post = communityPostRepository.findById(request.getPostId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));

        CommunityPostBlock communityPostBlock = CommunityPostBlock.createBlock(user, post);
        communityPostBlockRepository.save(communityPostBlock);

        CommunityPostReport report = CommunityPostReport.createReport(user, request);
        communityPostReportRepository.save(report);

        return "게시물을 신고하였습니다.";
    }
}
