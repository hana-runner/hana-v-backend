package com.v.hana.exception.transaction;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.TransactionHttpCode;

public class TransactionIdNotFoundException extends BaseCodeException {

    public TransactionIdNotFoundException() {
        super(TransactionHttpCode.TRANSACTION_ID_NOT_FOUND);
    }
}
