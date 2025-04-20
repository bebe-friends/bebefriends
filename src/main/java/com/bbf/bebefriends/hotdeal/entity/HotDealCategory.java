package com.bbf.bebefriends.hotdeal.entity;

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
public class HotDealCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                // 핫딜 카테고리 식별자

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private HotDealCategory parentCategoryId;       // 상위 카테고리 식별자

    private String name;                            // 이름

    private Integer depth;                          // 깊이

}
