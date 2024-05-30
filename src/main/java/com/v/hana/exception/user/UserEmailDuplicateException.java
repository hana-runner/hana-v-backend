package com.v.hana.exception.user;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.UserHttpCode;

public class UserEmailDuplicateException extends BaseCodeException {

    public UserEmailDuplicateException() {
        super(UserHttpCode.USER_EMAIL_DUPLICATE);
    }
}
