package com.bbf.bebefriends.hotdeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class HotDealDto {

    public record HotDealRequest(

            @Schema(description = "핫딜 제목", example = "제목")
            @NotNull String name,

            @Schema(description = "핫딜 내용", example = "핫딜을 알려드립니다.")
            @NotNull String content,

            @Schema(description = "핫딜 대 카테고리 ID", example = "10")
            @NotNull Long hotDealCategoryId,

            @Schema(description = "세분류 카테고리 Id", example = "41")
            @NotNull Long detailCategoryId,

            @NotNull
            boolean age_0,

            @NotNull
            boolean age_1,

            @NotNull
            boolean age_2,

            @NotNull
            boolean age_3,

            @NotNull
            boolean age_4,

            @NotNull
            boolean age_5,

            @NotNull
            boolean age_6,

            @NotNull
            boolean age_7,

            @Schema(description = "핫딜 단위", example = "개")
            @NotNull String unit,

            @Schema(description = "검색 최저가", example = "20000")
            @NotNull Integer searchPrice,

            @Schema(description = "핫딜 가격", example = "15000")
            @NotNull Integer hotDealPrice,

            @Schema(description = "비고", example = "토스페이")
            @NotNull String note,

            @Schema(description = "핫딜 기록 등록 날짜", example = "2025-06-18")
            @NotNull LocalDateTime date

    ) {}

    @Schema(description = "핫딜 상태 변경 요청")
    public record HotDealStatusRequest(
            @Schema(description = "핫딜 ID", example = "1")
            @NotNull Long id,

            @Schema(description = "핫딜 상태 값", example = "true")
            @NotNull boolean status
    ) {}

    public record HotDealSearchResponse(
            @Schema(description = "핫딜 ID", example = "1")
            Long id,

            @Schema(description = "핫딜 제목", example = "유아 장난감 특가")
            String name,

            @Schema(description = "핫딜 내용", example = "인기 장난감 특가 상품입니다.")
            String content,

            @Schema(description = "세분류 카테고리 정보")
            CategoryInfo detailCategory,

            @Schema(description = "등록 날짜")
            LocalDateTime createdDate
    ) {
        public record CategoryInfo(
                Long id,
                String name,
                Integer depth
        ) {}
    }
}
