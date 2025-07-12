package com.bbf.bebefriends.global.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AgeRange {
    AGE_0(0), AGE_1(1), AGE_2(2), AGE_3(3),
    AGE_4(4), AGE_5(5), AGE_6(6), AGE_7(7);

    private final int code;

    AgeRange(int code) {
        this.code = code;
    }

    public static AgeRange of(int code) {
        return Arrays.stream(values())
                .filter(e -> e.code == code)
                .findFirst()
                .orElseThrow();
    }
}
