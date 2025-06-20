package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.hotdeal.dto.HotDealCategoryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "hot_deal_category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private HotDealCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotDealCategory> subCategories = new ArrayList<>();

    private String name;

    private Integer depth;

    public void update(HotDealCategoryDto hotDealCategoryDto) {
        this.name = hotDealCategoryDto.getName();
        this.depth = hotDealCategoryDto.getDepth();
    }

    public void updateParentCategory(HotDealCategory hotDealCategory) {
        this.parentCategory = hotDealCategory;
    }

    public void addSubCategory(HotDealCategory subCategory) {
        this.subCategories.add(subCategory);
        subCategory.updateParentCategory(this); // 하위 카테고리의 부모를 설정
    }

    public void removeSubCategory(HotDealCategory subCategory) {
        this.subCategories.remove(subCategory);
        subCategory.updateParentCategory(null); // 하위 카테고리의 부모를 해제
    }


}
