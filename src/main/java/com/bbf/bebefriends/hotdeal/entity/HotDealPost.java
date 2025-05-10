package com.bbf.bebefriends.hotdeal.entity;

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
public class HotDealPost {

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

}
