package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import com.v.hana.common.response.BaseHttpReason;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExampleHttpCode implements BaseHttpCode {
    EXAMPLE_IS_0(HttpStatus.BAD_REQUEST, "EXAMPLE-001", "요청 데이터가 0이면 예외가 발생합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    ExampleHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
