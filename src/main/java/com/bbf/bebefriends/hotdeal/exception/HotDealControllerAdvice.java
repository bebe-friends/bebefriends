package com.bbf.bebefriends.hotdeal.exception;

import com.bbf.bebefriends.global.exception.GeneralException;
import com.bbf.bebefriends.global.exception.ResponseCode;

public class HotDealControllerAdvice extends GeneralException {
    public HotDealControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
}
