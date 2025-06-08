package com.bbf.bebefriends.global.spec;

import com.bbf.bebefriends.community.dto.CommunityPostDTO.PostListResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Schema(
        name        = "CursorPageResponseOfPostListResponse",
        description = "커서 기반 페이징 응답 스펙 (PostListResponse 전용)"
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CursorPageResponseOfPostListResponse {

    @ArraySchema(
            arraySchema = @Schema(description = "게시물 목록"),
            schema      = @Schema(implementation = PostListResponse.class)
    )
    private List<PostListResponse> items;

    @Schema(description = "마지막 커서 ID (이 값 이후의 데이터를 조회)", example = "102")
    private Long lastCursorId;

    @Schema(description = "다음 페이지 데이터 존재 여부", example = "true")
    private boolean hasNext;
}
