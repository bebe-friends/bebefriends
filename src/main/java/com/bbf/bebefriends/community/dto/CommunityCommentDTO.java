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
        private String content;
    }

    // 수정
    @Data
    public static class UpdateCommentRequest {

    }

    // 삭제
    @Data
    public static class DeleteCommentRequest {

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
