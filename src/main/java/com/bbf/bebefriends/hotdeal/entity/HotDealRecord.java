package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "hot_deal_record")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hot_deal_id")
    private HotDeal hotDeal;

    private LocalDateTime date;

    private String note;

    @Column(name = "search_price")
    private Integer searchPrice;

    @Column(name = "hot_deal_price")
    private Integer hotDealPrice;

}
