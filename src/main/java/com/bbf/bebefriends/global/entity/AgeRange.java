package com.bbf.bebefriends.global.entity;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.hotdeal.exception.HotDealControllerAdvice;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum AgeRange {
    AGE_00(0), AGE_0(1), AGE_1(2), AGE_2(3),
    AGE_3(4), AGE_4(5), AGE_5(6), AGE_6(7);

    private final int code;

    AgeRange(int code) {
        this.code = code;
    }

//    public static AgeRange of(int code) {
//        return Arrays.stream(values())
//                .filter(e -> e.code == code)
//                .findFirst()
//                .orElseThrow();
//    }

    public static AgeRange of(int code) {
        return Arrays.stream(values())
                .filter(g -> g.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new HotDealControllerAdvice(ResponseCode._BAD_REQUEST));
    }
}
