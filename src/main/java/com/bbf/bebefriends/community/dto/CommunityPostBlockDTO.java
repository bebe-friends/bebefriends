package com.bbf.bebefriends.community.dto;

import lombok.Data;
import lombok.NonNull;

public class CommunityPostBlockDTO {
    @Data
    public static class PostBlockRequest {
        @NonNull
        private Long postId;
        @NonNull
        private String reason;
        @NonNull
        private String content;
    }
}
