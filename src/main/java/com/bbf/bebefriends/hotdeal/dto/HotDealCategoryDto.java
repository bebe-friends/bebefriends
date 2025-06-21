package com.bbf.bebefriends.hotdeal.dto;

import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class HotDealCategoryDto {

    private Long id;
    private Long parentCategoryId;
    private String name;
    private Integer depth;

    public static HotDealCategoryDto fromEntity(HotDealCategory hotDealCategory) {
        HotDealCategoryDto hotDealCategoryDto = HotDealCategoryDto.builder()
                .id(hotDealCategory.getId())
                .name(hotDealCategory.getName())
                .depth(hotDealCategory.getDepth())
                .build();

        if (hotDealCategory.getParentCategory() != null) {
            hotDealCategoryDto = hotDealCategoryDto.toBuilder()
                    .parentCategoryId(hotDealCategory.getParentCategory().getId())
                    .build();
        }

        return hotDealCategoryDto;
    }

}
