package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.community.dto.CommunityPostReportDTO;
import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommunityReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private CommunityComment comment;

    @Enumerated(EnumType.STRING)
    private BlockReason reason;
    private String content;

    public static CommunityReport createPostReport(User user, CommunityPost post, CommunityPostReportDTO.PostReportRequest request) {
        CommunityReport report = new CommunityReport();

        report.post = post;
        report.user = user;
        report.content = request.getContent();
        report.reason = BlockReason.valueOf(request.getReason());

        return report;
    }

    public static CommunityReport createCommentReport(User user, CommunityComment comment, CommunityPostReportDTO.CommentReportRequest request) {
        CommunityReport report = new CommunityReport();

        report.comment = comment;
        report.user = user;
        report.content = request.getContent();
        report.reason = BlockReason.valueOf(request.getReason());

        return report;
    }
}
