package com.v.hana.common.exception;

import com.v.hana.common.annotation.MethodInfo;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseExceptionResponse {
    private final boolean success;
    private final String timestamp;
    private final String code;
    private final String error;
    private final String message;

    @Builder(access = AccessLevel.PRIVATE)
    public BaseExceptionResponse(
            boolean success, String timestamp, String code, String error, String message) {
        this.success = success;
        this.timestamp = timestamp;
        this.code = code;
        this.error = error;
        this.message = message;
    }

    @MethodInfo(name = "of", description = "예외 응답 객체를 생성합니다.")
    public static BaseExceptionResponse of(
            HttpStatus httpStatus, String code, String message, String error) {
        return BaseExceptionResponse.builder()
                .success(false)
                .timestamp(LocalDateTime.now().toString())
                .code(code)
                .error(error)
                .message(message)
                .build();
    }

    @MethodInfo(name = "of", description = "예외 응답 객체를 생성합니다.")
    public static BaseExceptionResponse of(final BaseCodeException baseCodeException) {
        return BaseExceptionResponse.builder()
                .success(false)
                .timestamp(LocalDateTime.now().toString())
                .code(baseCodeException.getBaseHttpCode().getHttpReason().getCode())
                .message(baseCodeException.getBaseHttpCode().getHttpReason().getMessage())
                .error(
                        baseCodeException
                                .getBaseHttpCode()
                                .getHttpReason()
                                .getHttpStatus()
                                .getReasonPhrase())
                .build();
    }
}
