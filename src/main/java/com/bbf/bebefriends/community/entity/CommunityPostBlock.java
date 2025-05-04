package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.community.dto.CommunityPostBlockDTO;
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

    private PostBlockReason reason;
    private String content;

    // FIXME: 신고사유 수정
    public static CommunityPostBlock createBlock(User user, CommunityPost post, CommunityPostBlockDTO.PostBlockRequest request) {
        CommunityPostBlock block = new CommunityPostBlock();

        block.user = user;
        block.post = post;
        block.content = request.getContent();
        block.reason = PostBlockReason.valueOf(request.getReason());

        return block;
    }
}
