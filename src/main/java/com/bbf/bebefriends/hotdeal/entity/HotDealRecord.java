package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String note;

    @Column(name = "search_price")
    private Integer searchPrice;

    @Column(name = "hot_deal_price")
    private Integer hotDealPrice;

    public void update(String note, Integer searchPrice, Integer hotDealPrice) {
        this.note = note;
        this.searchPrice = searchPrice;
        this.hotDealPrice = hotDealPrice;
    }

    public static HotDealRecord createHotDealRecord(
            HotDeal hotDeal,
            String note,
            Integer searchPrice,
            Integer hotDealPrice
    ) {
        return HotDealRecord.builder()
                .hotDeal(hotDeal)
                .note(note)
                .searchPrice(searchPrice)
                .hotDealPrice(hotDealPrice)
                .build();
    }


}
