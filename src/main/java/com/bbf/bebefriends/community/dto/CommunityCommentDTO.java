package com.bbf.bebefriends.community.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public class CommunityCommentDTO {
    // 생성
    @Data
    public static class CreateCommentRequest {
        @NonNull
        private Long postId;

        private Long parentId;

        @NonNull
        private String content;
    }

    @Data
    public static class CreateCommentResponse {

    }

    // 수정
    @Data
    public static class UpdateCommentRequest {
        @NonNull
        private Long commentId;

        @NonNull
        private String content;
    }

    @Data
    public static class UpdateCommentResponse {

    }

    // 삭제
    @Data
    public static class DeleteCommentRequest {
        @NonNull
        private Long commentId;
    }

    @Data
    public static class DeleteCommentResponse {

    }

    // 댓글 상세
    @Data
    @Builder
    public static class CommentDetails {
        private Long commentId;
        private String commenter;
        private String content;
        private LocalDateTime createdAt;
//        private List<CommentDetails> replies;
    }

    /**
     * 네이티브 쿼리 또는 JPQL 결과의 컬럼을
     * 인터페이스의 메서드에 매핑하기 위한 Projection 인터페이스
     */
    public interface CommentCursorProjection {
        // 부모 댓글 관련
        Long getParentId();          // parent.comment_id
        Long getAuthorId();
        String getParentContent();     // parent.content
        LocalDateTime getParentCreatedDate(); // parent.created_date

        // 자식 댓글 관련
        Long getChildId();
        Long getChildAuthorId();
        String getChildContent();
        LocalDateTime getChildCreatedDate();

        /**
         * 자식ID가 null이면 “대댓글이 없는 부모”라는 의미로, parent-only 레코드를 판별
         */
        default boolean isParentOnly() {
            return getChildId() == null;
        }
    }

    /**
     * 커서를 한 쌍으로 묶는 간단한 DTO
     */
    @Getter
    public static class CommentCursor {
        private final Long parentOffset;
        private final Long childOffset;

        public CommentCursor(Long parentOffset, Long childOffset) {
            this.parentOffset = parentOffset;
            this.childOffset  = childOffset;
        }
    }

    @Getter
    public static class CommentDetailsResponse {
        private final List<CommentCursorProjection> items;
        private final CommentCursor nextCommentCursor;

        public CommentDetailsResponse(List<CommentCursorProjection> items, CommentCursor nextCommentCursor) {
            this.items = items;
            this.nextCommentCursor = nextCommentCursor;
        }
    }
}
