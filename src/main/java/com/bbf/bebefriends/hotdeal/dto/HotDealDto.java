package com.bbf.bebefriends.hotdeal.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealDto {

    private Long hotDealCategoryId;     // 카테고리 식별자

    private String name;                // 이름

    private String imgPath;             // 이미지 경로

    private String unit;                // 단위

    private String status;              // 상태

}
