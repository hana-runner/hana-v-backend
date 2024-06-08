package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import com.v.hana.common.response.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum UserInterestHttpCode implements BaseHttpCode {
    USER_INTEREST_DUPLICATED(HttpStatus.BAD_REQUEST, "USERINTEREST-001", "사용자 관심사가 존재합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    UserInterestHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
