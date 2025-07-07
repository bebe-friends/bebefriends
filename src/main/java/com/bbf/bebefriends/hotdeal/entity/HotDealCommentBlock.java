package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "hotdeal_comment_block")
public class HotDealCommentBlock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotdeal_comment_id")
    private HotDealComment comment;

    public static HotDealCommentBlock createCommentBlock(User user, HotDealComment comment) {
        HotDealCommentBlock block = new HotDealCommentBlock();

        block.user = user;
        block.comment = comment;

        return block;
    }
}
