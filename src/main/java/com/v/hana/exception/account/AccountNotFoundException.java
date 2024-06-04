package com.v.hana.exception.account;

import com.v.hana.common.exception.AccountHttpCode;
import com.v.hana.common.exception.BaseCodeException;

public class AccountNotFoundException extends BaseCodeException {
    public AccountNotFoundException() {
        super(AccountHttpCode.ACCOUNT_NOT_FOUND);
    }
}
