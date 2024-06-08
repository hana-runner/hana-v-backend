package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import com.v.hana.common.response.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum ImageHttpCode implements BaseHttpCode {

    IMAGE__UPLOAD_FAILED(HttpStatus.NOT_FOUND , "IMAGE-001", "이미지를 업로드에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason(){
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    ImageHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
