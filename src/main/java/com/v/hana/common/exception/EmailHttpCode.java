package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import com.v.hana.common.response.BaseHttpReason;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum EmailHttpCode implements BaseHttpCode {
    INVALID_AUTH_CODE(HttpStatus.BAD_REQUEST, "EMAIL-001", "입력 받은 인증 코드가 잘못됨.");
    
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    EmailHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
