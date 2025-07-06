package com.bbf.bebefriends.hotdeal.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class HotDealCategoryDto {

    public record HotDealResponse(

            @Schema(description = "현재 카테고리 ID", example = "1")
            @NotNull @Positive Long id,

            @Schema(description = "현재 카테고리의 상위 카테고리 ID 값", example = "3")
            @NotNull @Positive Long parentCategoryId,

            @Schema(description = "카테고리 명", example = "가구")
            @NotBlank String name,

            @Schema(description = "카테고리 분류 (대:1, 중:2, 소:3, 세:4)", example = "1")
            @NotNull @Min(1) @Max(4) Integer depth
    ) {}

    public record HotDealCategoryRequest(

            @Schema(description = "등록할 핫딜 카테고리의 상위 카테고리 ID", example = "10")
            @NotBlank String parentCategoryId,

            @Schema(description = "등록할 핫딜 카테고리 명", example = "레고랜드")
            @NotBlank String targetCategory
    ) {}

    public record CategoryUpdateRequest(
            @Schema(description = "수정할 카테고리 ID", example = "1")
            @NotNull @Positive Long categoryId,

            @Schema(description = "변경할 카테고리 이름", example = "디지털/가전")
            @NotBlank String newName
    ) {}

    public record CategoryRequest (
            @Schema(description = "핫딜 상품의 ID", example = "10")
            @NotNull @Positive Long hotDealId,

            @Schema(description = "핫딜 상품 중분류 카테고리 명", example = "체험")
            @NotBlank String middleCategory,

            @Schema(description = "핫딜 상품 소분류 카테고리 명", example = "레고랜드")
            @NotBlank String smallCategory,

            @Schema(description = "핫딜 상품 세분류 카테고리 명", example = "레고랜드 이용권")
            @NotBlank String detailedCategory
    ) {}

}
