package com.v.hana.exception.transaction;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.ExpenseHttpCode;
import com.v.hana.common.response.BaseHttpCode;

public class ExpenseNotFoundException extends BaseCodeException {
    public ExpenseNotFoundException() {
        super(ExpenseHttpCode.EXPENSE_NOT_FOUND);
    }
}
