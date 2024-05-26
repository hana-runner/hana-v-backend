package com.v.hana.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseHttpReason {
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Builder
    public BaseHttpReason(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
