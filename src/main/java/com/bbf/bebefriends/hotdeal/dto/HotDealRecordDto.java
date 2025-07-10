package com.bbf.bebefriends.hotdeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class HotDealRecordDto {


    @Schema(description = "핫딜 기록 생성 요청")
    public record HotDealRecordRequest(
            @Schema(description = "핫딜 ID", example = "1")
            @NotNull Long hotDealId,

            @Schema(description = "검색 최저가", example = "20000")
            @NotNull Integer searchPrice,

            @Schema(description = "핫딜 가격", example = "15000")
            @NotNull Integer hotDealPrice,

            @Schema(description = "비고", example = "토스페이")
            @NotNull String note

    ) {}


    @Schema(description = "핫딜 기록 응답")
    public record HotDealRecordResponse(

            @Schema(description = "검색 최저가", example = "20000")
            @NotNull Integer searchPrice,

            @Schema(description = "핫딜 가격", example = "15000")
            @NotNull Integer hotDealPrice,

            @Schema(description = "비고", example = "토스페이")
            @NotNull String note,

            @Schema(description = "핫딜 기록 등록 날짜", example = "2025-06-18")
            @NotNull LocalDateTime date

    ) {}

    @Schema(description = "핫딜 기록 상세 응답")
    public record HotDealRecordDetailResponse(
            @Schema(description = "핫딜 기록 ID", example = "10")
            @NotNull Long id,

            @Schema(description = "핫딜 ID", example = "1")
            @NotNull Long hotDealId,

            @Schema(description = "핫딜 기록 등록 날짜", example = "2025-06-18")
            @NotNull LocalDateTime date,

            @Schema(description = "비고", example = "토스페이")
            @NotNull String note,

            @Schema(description = "검색 최저가", example = "20000")
            @NotNull Integer searchPrice,

            @Schema(description = "핫딜 가격", example = "15000")
            @NotNull Integer hotDealPrice
    ) {}

    @Schema(description = "핫딜 기록 업데이트 요청")
    public record HotDealRecordUpdateRequest(

            @Schema(description = "비고", example = "토스페이")
            @NotNull String note,

            @Schema(description = "검색 최저가", example = "20000")
            @NotNull Integer searchPrice,

            @Schema(description = "핫딜 가격", example = "15000")
            @NotNull Integer hotDealPrice
    ) {}

}
