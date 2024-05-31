package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import com.v.hana.common.response.BaseHttpReason;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CategoryHttpCode implements BaseHttpCode {
    CATEGORY_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY-001", "해당 ID의 카테고리가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    CategoryHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
