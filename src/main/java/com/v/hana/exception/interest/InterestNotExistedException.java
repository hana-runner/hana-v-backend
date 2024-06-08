package com.v.hana.exception.interest;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.InterestHttpCode;

public class InterestNotExistedException extends BaseCodeException {
    public InterestNotExistedException() {
        super(InterestHttpCode.INTEREST_NOT_EXISTED);
    }
}
