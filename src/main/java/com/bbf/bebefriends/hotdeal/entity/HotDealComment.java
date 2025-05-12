package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "hot_deal_comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                // 핫딜 댓글 식별자

    @ManyToOne
    @JoinColumn(name = "replied_comment_id")
    private HotDealComment repliedComment;          // 핫딜 대댓글 식별자

    @ManyToOne
    @JoinColumn(name = "hot_deal_post_id")
    private HotDealPost hotDealPost;                // 핫딜 게시글 식별자

    private String content;                         // 내용

    private LocalDateTime deleted_at;               // 삭제 여부

}
