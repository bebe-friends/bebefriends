package com.bbf.bebefriends.hotdeal.dto;

import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealPostDto {

    private Long hotDealId;             // 핫딜 식별자

    private String title;               // 핫딜 게시글 제목

    private String content;             // 핫딜 게시글 내용

    private String link;                // 링크

    private String imgPath;             // 이미지 경로

    private String status;              // 상태

    private Integer age;                // 나이

    // Entity -> dto
    public static HotDealPostDto fromEntity(HotDealPost hotDealPost) {
        return HotDealPostDto.builder()
                .hotDealId(hotDealPost.getHotDeal().getId())
                .title(hotDealPost.getTitle())
                .content(hotDealPost.getContent())
                .link(hotDealPost.getLink())
                .imgPath(hotDealPost.getImgPath())
                .status(hotDealPost.getStatus())
                .age(hotDealPost.getAge())
                .build();
    }

}
