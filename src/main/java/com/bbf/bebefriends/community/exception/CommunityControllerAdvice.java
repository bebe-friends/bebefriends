package com.bbf.bebefriends.community.exception;

import com.bbf.bebefriends.global.exception.GeneralException;
import com.bbf.bebefriends.global.exception.ResponseCode;

public class CommunityControllerAdvice extends GeneralException {
    public CommunityControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
}
