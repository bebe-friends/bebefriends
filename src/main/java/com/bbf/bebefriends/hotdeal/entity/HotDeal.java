package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "hot_deal")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDeal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                // 핫딜 식별자

    @ManyToOne
    @JoinColumn(name = "hot_deal_category_id")
    private HotDealCategory hotDealCategory;        // 핫딜 카테고리 식별자

    private String name;                            // 이름

    private String imgPath;                         // 이미지 경로

    private String unit;                            // 단위

    private String status;                          // 핫딜 상태

    public void update(HotDealDto hotDealDto) {
        this.name = hotDealDto.getName();
        this.imgPath = hotDealDto.getImgPath();
        this.unit = hotDealDto.getUnit();
        this.status = hotDealDto.getStatus();
    }

    public void updateHotDealCategory(HotDealCategory hotDealCategory) {
        this.hotDealCategory = hotDealCategory;
    }

}
