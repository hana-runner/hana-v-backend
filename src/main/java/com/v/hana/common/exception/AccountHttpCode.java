package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import com.v.hana.common.response.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum AccountHttpCode implements BaseHttpCode {
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "계좌 정보가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason(){
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    AccountHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
