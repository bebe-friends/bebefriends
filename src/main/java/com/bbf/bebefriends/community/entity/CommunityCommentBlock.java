package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "community_comment_block")
public class CommunityCommentBlock extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private CommunityComment comment;

    public static CommunityCommentBlock createCommentBlock(User user, CommunityComment comment) {
        CommunityCommentBlock block = new CommunityCommentBlock();

        block.user = user;
        block.comment = comment;

        return block;
    }
}
