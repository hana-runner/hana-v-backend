package com.v.hana.exception.email;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.EmailHttpCode;
import com.v.hana.common.exception.UserHttpCode;

public class InvalidEmailAuthCodeException extends BaseCodeException{
    public InvalidEmailAuthCodeException() {
        super(EmailHttpCode.INVALID_AUTH_CODE);
    }
}
