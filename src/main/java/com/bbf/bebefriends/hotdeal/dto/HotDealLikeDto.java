package com.bbf.bebefriends.hotdeal.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealLikeDto {

    private Long hotDealPostId;     // 핫딜 게시글 식별자

    private Boolean likeChk;        // 핫딜 좋아요 여부

}
