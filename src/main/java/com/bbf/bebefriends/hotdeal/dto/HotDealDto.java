package com.bbf.bebefriends.hotdeal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class HotDealDto {

    public record HotDealRequest(

            @Schema(description = "핫딜 제목", example = "제목")
            @NotNull String name,

            @Schema(description = "핫딜 내용", example = "핫딜을 알려드립니다.")
            @NotNull String content,

            @Schema(description = "핫딜 링크", example = "http://coupang.com/ABC")
            @NotNull List<String> links,

            @Schema(description = "핫딜 대 카테고리 ID", example = "1")
            @NotNull Long hotDealCategoryId,

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
            boolean age_6

    ) {}

    @Schema(description = "핫딜 상태 변경 요청")
    public record HotDealStatusRequest(
            @Schema(description = "핫딜 ID", example = "1")
            @NotNull Long id,

            @Schema(description = "핫딜 상태 값", example = "true")
            @NotNull boolean status
    ) {}

}
