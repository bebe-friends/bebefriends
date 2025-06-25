package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "hot_deal_comment")
public class HotDealComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                // 핫딜 댓글 식별자

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                              // 회원 식별자

    @ManyToOne
    @JoinColumn(name = "replied_comment_id")
    private HotDealComment repliedComment;          // 핫딜 대댓글 식별자

    @ManyToOne
    @JoinColumn(name = "hot_deal_post_id")
    private HotDealPost hotDealPost;                // 핫딜 게시글 식별자

    private String content;                         // 내용

    private LocalDateTime deletedAt;                // 삭제일자

    public void update(HotDealCommentDto hotDealCommentDto) {
        this.content = hotDealCommentDto.getContent();

    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();

    }

}
