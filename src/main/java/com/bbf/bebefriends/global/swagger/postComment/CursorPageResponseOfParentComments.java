package com.bbf.bebefriends.global.swagger.postComment;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO.ParentCommentResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Schema(
        name        = "CursorPageResponseOfParentComments",
        description = "커서 기반 페이징 응답 스펙 (ParentCommentResponse 전용)"
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CursorPageResponseOfParentComments {

    @ArraySchema(
            arraySchema = @Schema(description = "부모 댓글 목록, 대댓글 3개까지"),
            schema      = @Schema(implementation = ParentCommentResponse.class)
    )
    private List<ParentCommentResponse> items;

    @Schema(description = "마지막 커서 ID (이 값 이후의 데이터를 조회)", example = "102")
    private Long lastCursorId;
}
