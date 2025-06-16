package com.bbf.bebefriends.community.dto;

import lombok.Data;
import lombok.NonNull;

public class CommunityPostReportDTO {
    @Data
    public static class PostReportRequest {
        @NonNull
        private Long postId;
        @NonNull
        private String reason;
        @NonNull
        private String content;
    }

    @Data
    public static class CommentReportRequest {
        @NonNull
        private Long commentId;
        @NonNull
        private String reason;
        @NonNull
        private String content;
    }
}
