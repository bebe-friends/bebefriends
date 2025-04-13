package com.bbf.bebefriends.member.dto;

import jakarta.validation.constraints.NotBlank;

public record FcmTokenUpdateRequest(
        @NotBlank String newFcmToken
) {}
