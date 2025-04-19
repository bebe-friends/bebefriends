package com.bbf.bebefriends.community.dto;

import lombok.Data;
import lombok.NonNull;

public class CommunityPostLikeDTO {
    @Data
    public static class PostLikeRequest {
        @NonNull
        private Long memberId;
    }
}
