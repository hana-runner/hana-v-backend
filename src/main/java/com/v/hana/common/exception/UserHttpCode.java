package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import com.v.hana.common.response.BaseHttpReason;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserHttpCode implements BaseHttpCode {
    USER_EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, "USER-001", "입력 받은 사용자 이메일이 이미 존재함."),
    USER_NAME_DUPLICATE(HttpStatus.BAD_REQUEST, "USER-002", "입력 받은 사용자 이름이 이미 존재함."),
    EMPTY_USER(HttpStatus.BAD_REQUEST, "USER-003", "사용자 정보가 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    UserHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
