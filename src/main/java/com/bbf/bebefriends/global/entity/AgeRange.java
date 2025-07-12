package com.bbf.bebefriends.global.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AgeRange {
    AGE_00(0), AGE_0(1), AGE_1(2), AGE_2(3),
    AGE_3(4), AGE_4(5), AGE_5(6), AGE_6(7);

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
