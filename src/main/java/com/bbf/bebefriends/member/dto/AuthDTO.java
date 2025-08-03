package com.bbf.bebefriends.member.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthDTO {

    public record NicknameUpdateRequest(
            @NotBlank String nickname
    ) {}

    public record FcmTokenUpdateRequest(
            @NotBlank String newFcmToken
    ) {}

}
