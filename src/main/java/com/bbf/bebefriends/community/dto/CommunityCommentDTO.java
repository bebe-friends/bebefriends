package com.bbf.bebefriends.community.dto;

import lombok.Builder;
import lombok.Data;
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
        private List<CommentDetails> replies;
    }
}
