package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import com.v.hana.common.response.BaseHttpReason;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExpenseHttpCode implements BaseHttpCode {
    EXPENSE_NOT_FOUND(HttpStatus.NOT_FOUND, "EXPENSE-001", "지출내역이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    ExpenseHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
