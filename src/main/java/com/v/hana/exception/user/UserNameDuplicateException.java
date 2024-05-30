package com.v.hana.exception.user;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.UserHttpCode;

public class UserNameDuplicateException extends BaseCodeException {

    public UserNameDuplicateException() {
        super(UserHttpCode.USER_NAME_DUPLICATE);
    }
}
