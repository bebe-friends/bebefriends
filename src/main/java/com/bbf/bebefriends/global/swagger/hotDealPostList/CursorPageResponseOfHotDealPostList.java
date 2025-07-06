package com.bbf.bebefriends.global.swagger.hotDealPostList;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(
        name        = "CursorPageResponseOfPostList",
        description = "커서 기반 페이징 응답 스펙 (PostListResponse 전용)"
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CursorPageResponseOfHotDealPostList {
    @ArraySchema(
            arraySchema = @Schema(description = "게시물 목록"),
            schema      = @Schema(implementation = HotDealPostDto.HotDealListResponse.class)
    )
    private List<HotDealPostDto.HotDealListResponse> items;

    @Schema(description = "마지막 커서 ID (이 값 이후의 데이터를 조회)", example = "102")
    private Long lastCursorId;
}