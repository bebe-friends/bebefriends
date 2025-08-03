package com.bbf.bebefriends.member.util;

import java.util.regex.Pattern;

public class NicknameValidator {
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣]{2,15}$");

    public static boolean isValid(String nickname) {
        return nickname != null && !nickname.contains(" ") && NICKNAME_PATTERN.matcher(nickname).matches();
    }
}
