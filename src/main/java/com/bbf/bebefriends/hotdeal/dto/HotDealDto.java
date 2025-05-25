package com.bbf.bebefriends.hotdeal.dto;

import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealDto {

    private Long id;                    // 핫딜 식별자

    private Long hotDealCategoryId;     // 카테고리 식별자

    private String name;                // 이름

    private String imgPath;             // 이미지 경로

    private String unit;                // 단위

    private String status;              // 상태

    // Entity -> dto
    public static HotDealDto fromEntity(HotDeal hotDeal) {
        return HotDealDto.builder()
                .id(hotDeal.getId())
                .hotDealCategoryId(hotDeal.getHotDealCategory().getId())
                .name(hotDeal.getName())
                .imgPath(hotDeal.getImgPath())
                .status(hotDeal.getStatus())
                .build();

    }

}
