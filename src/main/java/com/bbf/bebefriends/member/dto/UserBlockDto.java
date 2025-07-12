package com.bbf.bebefriends.member.dto;

import java.time.LocalDateTime;

public class UserBlockDto {
    public record BlockedUserResponse(
            Long userId,
            String nickname,
            LocalDateTime blockedAt
    ) {}

    public record BlockedPostResponse(
            Long postId,
            String title,
            String nickname,
            LocalDateTime blockedAt
    ) {}
}
