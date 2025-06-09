package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.community.dto.CommunityPostReportDTO;
import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommunityPostReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    private PostBlockReason reason;
    private String content;

    public static CommunityPostReport createReport(User user, CommunityPostReportDTO.PostReportRequest request) {
        CommunityPostReport report = new CommunityPostReport();

        report.user = user;
        report.content = request.getContent();
        report.reason = PostBlockReason.valueOf(request.getReason());

        return report;
    }
}
