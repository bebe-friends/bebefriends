package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.hotdeal.dto.HotDealRecordDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "hot_deal_record")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 핫딜 기록 식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hot_deal_id")
    private HotDeal hotDeal;            // 핫딜 식별자

    private LocalDate date;             // 날짜

    private String note;                // 비고

    @Column(name = "search_price")
    private Integer searchPrice;        // 검색가

    @Column(name = "hot_deal_price")
    private Integer hotDealPrice;       // 핫딜가

    public void update(HotDealRecordDto hotDealRecordDto) {
        this.date = hotDealRecordDto.getDate();
        this.note = hotDealRecordDto.getNote();
        this.searchPrice = hotDealRecordDto.getSearchPrice();
        this.hotDealPrice = hotDealRecordDto.getHotDealPrice();
    }

    public void updateHotDeal(HotDeal hotDeal) {
        this.hotDeal = hotDeal;
    }

}
