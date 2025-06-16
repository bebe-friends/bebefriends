package com.bbf.bebefriends.community.dto;

import lombok.*;

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

    // 수정
    @Data
    public static class UpdateCommentRequest {
        @NonNull
        private Long commentId;

        @NonNull
        private String content;
    }

    // 삭제
    @Data
    public static class DeleteCommentRequest {
        @NonNull
        private Long commentId;
    }

    // 댓글 상세
    @Data
    @Builder
    public static class ParentCommentResponse {
        private Long commentId;
        private Long authorId;
        private String authorName;
        private String content;
        private LocalDateTime createdAt;
        private long totalReplyCount;
        private Boolean hasMoreComment;
        private Boolean isDeleted;
        private Boolean isBlocked;
        private List<ChildCommentDTO> replyComments;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ChildCommentDTO {
        private Long parentId;
        private Long commentId;
        private String authorName;
        private String content;
        private LocalDateTime createdAt;
        private Boolean isDeleted;
        private Boolean isBlocked;
    }

    /**
     * 네이티브 쿼리 또는 JPQL 결과의 컬럼을
     * 인터페이스의 메서드에 매핑하기 위한 Projection 인터페이스
     */
    public interface CommentCursorProjection {
        // 부모 댓글 관련
        Long getParentId();          // parent.comment_id
        Long getParentUserId();
        String getParentAuthorName();
        String getParentContent();     // parent.content
        LocalDateTime getParentCreatedDate(); // parent.created_date

        // 자식 댓글 관련
        Long getChildId();
        Long getChildUserId();
        String getChildAuthorName();
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
