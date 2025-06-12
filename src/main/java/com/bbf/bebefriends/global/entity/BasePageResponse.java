package com.bbf.bebefriends.global.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(
        name        = "BasePageResponse",
        description = "커서 기반 페이징 응답 스펙"
)
@Getter
@Builder
@AllArgsConstructor
public class BasePageResponse<T> {
    @Schema(description = "페이지네이션 아이템 목록")
    private List<T> items;

    @Schema(description = "마지막 커서 ID (다음 조회 시 이 ID 이후의 데이터를 조회)")
    private Long lastCursorId;

    public static <T> BasePageResponse<T> of(List<T> items, Long lastCursorId) {
        return BasePageResponse.<T>builder()
                .items(items)
                .lastCursorId(lastCursorId)
                .build();
    }
}
