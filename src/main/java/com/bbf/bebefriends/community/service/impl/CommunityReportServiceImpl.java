package com.bbf.bebefriends.community.service.impl;

import com.bbf.bebefriends.community.dto.CommunityPostReportDTO;
import com.bbf.bebefriends.community.entity.*;
import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.community.repository.*;
import com.bbf.bebefriends.community.service.CommunityReportService;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.exception.UserControllerAdvice;
import com.bbf.bebefriends.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityReportServiceImpl implements CommunityReportService {
    private final CommunityPostReportRepository communityPostReportRepository;
    private final CommunityPostBlockRepository communityPostBlockRepository;
    private final CommunityCommentBlockRepository communityCommentBlockRepository;
    private final CommunityUserBlockRepository communityUserBlockRepository;
    private final CommunityPostRepository communityPostRepository;
    private final CommunityCommentRepository communityCommentRepository;
    private final UserRepository userRepository;

    @Override
    public String reportPost(User user, CommunityPostReportDTO.PostReportRequest request) {
        CommunityPost post = communityPostRepository.findById(request.getPostId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));

        // 차단 엔티티
        CommunityPostBlock communityPostBlock = CommunityPostBlock.createPostBlock(user, post);
        communityPostBlockRepository.save(communityPostBlock);

        // 신고 엔티티
        CommunityReport report = CommunityReport.createPostReport(user, post, request);
        communityPostReportRepository.save(report);

        return "게시물을 신고하였습니다.";
    }

    @Override
    public String blockPost(User user, Long postId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));

        CommunityPostBlock communityPostBlock = CommunityPostBlock.createPostBlock(user, post);
        communityPostBlockRepository.save(communityPostBlock);

        return "게시물을 차단하였습니다.";
    }

    @Override
    public String unblockPost(User user, Long postId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));

        CommunityPostBlock communityPostBlock = communityPostBlockRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_BLOCK_NOT_FOUND));
        communityPostBlockRepository.delete(communityPostBlock);

        return "게시물이 차단 해제되었습니다.";
    }

    @Override
    public String reportComment(User user, CommunityPostReportDTO.CommentReportRequest request) {
        CommunityComment comment = communityCommentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));

        // 차단 엔티티
        CommunityCommentBlock communityCommentBlock = CommunityCommentBlock.createCommentBlock(user, comment);
        communityCommentBlockRepository.save(communityCommentBlock);

        // 신고 엔티티
        CommunityReport report = CommunityReport.createCommentReport(user, comment, request);
        communityPostReportRepository.save(report);

        return "댓글을 신고했습니다.";
    }

    @Override
    public String unblockComment(User user, Long commentId) {
        CommunityComment comment = communityCommentRepository.findById(commentId)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_POST_NOT_FOUND));

        CommunityCommentBlock communityCommentBlock = communityCommentBlockRepository.findByCommentAndUser(comment, user)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.COMMUNITY_COMMENT_BLOCK_NOT_FOUND));
        communityCommentBlockRepository.delete(communityCommentBlock);

        return "댓글이 차단 해제되었습니다.";
    }

    @Override
    public String blockUser(User user, Long targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));

        Optional<CommunityUserBlock> userBlock = communityUserBlockRepository.findByUserAndBlockedUser(user, targetUser);
        if (userBlock.isPresent()) {
            throw new CommunityControllerAdvice(ResponseCode.ALREADY_BLOCK_USER);
        }
        CommunityUserBlock communityUserBlock = CommunityUserBlock.createUserBlock(user, targetUser);
        communityUserBlockRepository.save(communityUserBlock);

        return "유저를 차단했습니다.";
    }

    @Override
    public String unblockUser(User user, Long targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));

        CommunityUserBlock communityUserBlock = communityUserBlockRepository.findByUserAndBlockedUser(user, targetUser)
                .orElseThrow(() -> new CommunityControllerAdvice(ResponseCode.USER_BLOCK_NOT_FOUND));
        communityUserBlockRepository.delete(communityUserBlock);

        return "유저를 차단 해제했습니다.";
    }
}
