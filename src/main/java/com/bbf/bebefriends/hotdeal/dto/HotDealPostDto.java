package com.bbf.bebefriends.hotdeal.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealPostDto {

    private Long hotDealId;     // 핫딜 식별자

    private String title;       // 핫딜 게시글 제목

    private String content;     // 핫딜 게시글 내용

    private String link;        // 링크

    private String imgPath;     // 이미지 경로

    private String status;      // 상태

    private Integer age;        // 나이

}
