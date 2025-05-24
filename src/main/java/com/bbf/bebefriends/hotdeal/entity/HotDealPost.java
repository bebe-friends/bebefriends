package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "hot_deal_post")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 핫딜 게시글 식별자

    @ManyToOne
    @JoinColumn(name = "hot_deal_id")
    private HotDeal hotDeal;        // 핫딜 식별자

    private String title;           // 제목

    private String content;         // 내용

    private String link;            // 링크

    private String imgPath;         // 이미지 경로

    private String status;          // 핫딜 상태

    private Integer age;            // 나이

    private int viewCount;          // 조회수

    private int likeCount;          // 좋아요 수

    // 게시글 조회수 증가
    public void increaseViewCount() {
        this.viewCount++;
    }

    // 게시물 좋아요
    public void increaseLikeCount() {
        this.likeCount++;
    }

    // 게시물 좋아요 취소
    public void decreaseLikeCount() {
        this.likeCount--;
    }

}
