package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import com.v.hana.common.response.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum InterestHttpCode implements BaseHttpCode {
    INTEREST_NOT_EXISTED(HttpStatus.NOT_FOUND, "INTEREST-001", "관심사가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason(){
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    InterestHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}

