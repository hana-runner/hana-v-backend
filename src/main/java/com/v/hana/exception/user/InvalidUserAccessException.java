package com.v.hana.exception.user;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.UserHttpCode;

public class InvalidUserAccessException extends BaseCodeException {

    public InvalidUserAccessException() {
        super(UserHttpCode.EMPTY_USER);
    }
}
