package com.bbf.bebefriends.hotdeal.dto;

import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealPostDto {

    private Long id;                    // 핫딜 게시글 식별자

    private Long userId;                // 회원 식별자

    private Long hotDealId;             // 핫딜 식별자

    private String title;               // 핫딜 게시글 제목

    private String content;             // 핫딜 게시글 내용

    private String link;                // 링크

    private String imgPath;             // 이미지 경로

    private String status;              // 상태

    private Integer age;                // 나이

    private int viewCount;              // 조회수

    private int likeCount;              // 좋아요 수

    // Entity -> dto
    public static HotDealPostDto fromEntity(HotDealPost hotDealPost) {
        return HotDealPostDto.builder()
                .id(hotDealPost.getId())
                .userId(hotDealPost.getUser().getUid())
                .hotDealId(hotDealPost.getHotDeal().getId())
                .title(hotDealPost.getTitle())
                .content(hotDealPost.getContent())
                .link(hotDealPost.getLink())
                .imgPath(hotDealPost.getImgPath())
                .status(hotDealPost.getStatus())
                .age(hotDealPost.getAge())
                .viewCount(hotDealPost.getViewCount())
                .likeCount(hotDealPost.getLikeCount())
                .build();
    }

}
