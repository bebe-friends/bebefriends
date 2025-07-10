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

    public void update(LocalDateTime date, String note, Integer searchPrice, Integer hotDealPrice) {
        this.date = date;
        this.note = note;
        this.searchPrice = searchPrice;
        this.hotDealPrice = hotDealPrice;
    }

    public static HotDealRecord createHotDealRecord(
            HotDeal hotDeal,
            LocalDateTime date,
            String note,
            Integer searchPrice,
            Integer hotDealPrice
    ) {
        return HotDealRecord.builder()
                .hotDeal(hotDeal)
                .date(date)
                .note(note)
                .searchPrice(searchPrice)
                .hotDealPrice(hotDealPrice)
                .build();
    }


}
