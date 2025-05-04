package com.bbf.bebefriends.member.dto;

import jakarta.validation.constraints.NotBlank;

public class TokenDTO {

    public record FcmTokenUpdateRequest(
            @NotBlank String newFcmToken
    ) {}

}
