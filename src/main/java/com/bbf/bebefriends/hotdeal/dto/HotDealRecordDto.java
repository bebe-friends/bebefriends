package com.bbf.bebefriends.hotdeal.dto;

import com.bbf.bebefriends.hotdeal.entity.HotDealRecord;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealRecordDto {

    private Long id;                    // 핫딜 기록 식별자

    private Long hotDealId;             // 핫딜 식별자

    private LocalDate date;             // 날짜

    private String note;                // 비고

    private Integer searchPrice;         // 검색가

    private Integer hotDealPrice;        // 핫딜가

    // Entity -> dto
    public static HotDealRecordDto fromEntity(HotDealRecord hotDealRecord) {
        return HotDealRecordDto.builder()
                .id(hotDealRecord.getId())
                .hotDealId(hotDealRecord.getHotDeal().getId())
                .date(hotDealRecord.getDate())
                .note(hotDealRecord.getNote())
                .searchPrice(hotDealRecord.getSearchPrice())
                .hotDealPrice(hotDealRecord.getHotDealPrice())
                .build();
    }


}
