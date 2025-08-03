package com.bbf.bebefriends.member.exception;

import com.bbf.bebefriends.global.exception.GeneralException;
import com.bbf.bebefriends.global.exception.ResponseCode;

public class UserControllerAdvice extends GeneralException {
    public UserControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
}
