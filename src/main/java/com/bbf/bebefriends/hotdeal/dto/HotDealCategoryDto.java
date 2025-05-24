package com.bbf.bebefriends.hotdeal.dto;

import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class HotDealCategoryDto {

    private Long id;                    // 카테고리 식별자

    private Long parentCategoryId;      // 상위 카테고리 식별자

    private String name;                // 이름

    private Integer depth;              // 깊이

    public static HotDealCategoryDto fromEntity(HotDealCategory hotDealCategory) {
        HotDealCategoryDto hotDealCategoryDto = HotDealCategoryDto.builder()
                .id(hotDealCategory.getId())
                .name(hotDealCategory.getName())
                .depth(hotDealCategory.getDepth())
                .build();

        // 상위 카테고리가 있는 경우
        if (hotDealCategory.getParentCategory() != null) {
            hotDealCategoryDto = hotDealCategoryDto.toBuilder()
                    .parentCategoryId(hotDealCategory.getParentCategory().getId())
                    .build();
        }

        return hotDealCategoryDto;
    }

}
