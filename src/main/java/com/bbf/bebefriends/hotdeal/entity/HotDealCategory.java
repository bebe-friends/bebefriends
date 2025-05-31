package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "hot_deal_category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                // 핫딜 카테고리 식별자

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private HotDealCategory parentCategory;         // 상위 카테고리 식별자

    private String name;                            // 이름

    private Integer depth;                          // 깊이

    public void update(HotDealCategoryDto hotDealCategoryDto) {
        this.name = hotDealCategoryDto.getName();
        this.depth = hotDealCategoryDto.getDepth();
    }

    public void updateParentCategory(HotDealCategory hotDealCategory) {
        this.parentCategory = hotDealCategory;
    }

}
