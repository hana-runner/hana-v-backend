package com.v.hana.exception.transaction;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.TransactionHttpCode;

public class ReadTransactionFailException extends BaseCodeException {
    public ReadTransactionFailException() {
        super(TransactionHttpCode.READ_TRANSACTION_FAIL);
    }
}
