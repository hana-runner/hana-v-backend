package com.v.hana.exception.interest;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.UserInterestHttpCode;

public class UserInterestDuplicatedException extends BaseCodeException {
    public UserInterestDuplicatedException() {
        super(UserInterestHttpCode.USER_INTEREST_DUPLICATED);
    }
}
