package com.bbf.bebefriends.global.swagger.postComment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@JsonIgnoreProperties({"success"})
@Schema(
        description = "BaseResponse<CursorPageResponse<T>> 전용 스펙"
)
@Getter
public class CommunityChildCommentsPageResponseSpec {
    @JsonProperty("isSuccess")
    @Schema(description = "성공 여부", example = "true")
    private boolean isSuccess;

    @Schema(description = "응답 코드", example = "OK")
    private String code;

    @Schema(description = "응답 메세지", example = "조회에 성공했습니다.")
    private String message;

    @Schema(
            description = "페이징 결과",
            implementation = CursorPageResponseOfChildComments.class
    )
    private CursorPageResponseOfParentComments result;
}
