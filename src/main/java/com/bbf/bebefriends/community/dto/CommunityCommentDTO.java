package com.bbf.bebefriends.community.dto;

import lombok.Data;
import lombok.NonNull;

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
    public static class CommentDetails {

    }
}
