package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "hot_deal_category")
@Getter
@Setter
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

    public void updateParentCategory(HotDealCategory newParent) {
        this.parentCategory = newParent;
        this.depth = (newParent == null) ? 1 : newParent.getDepth() + 1;
    }

    public void addSubCategory(HotDealCategory subCategory) {
        if (subCategories.contains(subCategory)) {
            return;
        }
        this.subCategories.add(subCategory);
        subCategory.updateParentCategory(this);
    }

    public void removeSubCategory(HotDealCategory subCategory) {
        if (subCategories.remove(subCategory)) {
            subCategory.updateParentCategory(null);
        }
    }

}
