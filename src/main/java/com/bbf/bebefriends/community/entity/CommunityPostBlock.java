package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.community.dto.CommunityPostReportDTO;
import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommunityPostBlock extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    public static CommunityPostBlock createBlock(User user, CommunityPost post) {
        CommunityPostBlock block = new CommunityPostBlock();

        block.user = user;
        block.post = post;

        return block;
    }
}
