package com.bbf.bebefriends.hotdeal.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealCategoryDto {

    private Long parentCategoryId;      // 상위 카테고리 식별자

    private String name;                // 이름

    private Integer depth;              // 이미지 경로

}
