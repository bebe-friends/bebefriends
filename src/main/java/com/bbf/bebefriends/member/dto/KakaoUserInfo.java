package com.bbf.bebefriends.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoUserInfo {
    private Long id;
    private String nickname;
    private String email;
    private String phone;
}