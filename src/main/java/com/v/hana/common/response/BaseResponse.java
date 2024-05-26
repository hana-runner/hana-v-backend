package com.v.hana.common.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponse<T> {
    private final boolean success;
    private final String timestamp;
    private final int status;
    private final String code;
    private final T data;
    private final String message;

    @Builder(access = AccessLevel.PRIVATE)
    public BaseResponse(
            boolean success, String timestamp, int status, String code, T data, String message) {
        this.success = success;
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> BaseResponse<T> ok(T data) {
        return BaseResponse.<T>builder()
                .success(true)
                .timestamp(LocalDateTime.now().toString())
                .status(GlobalHttpCode.OK.getHttpStatus().value())
                .code(GlobalHttpCode.OK.getCode())
                .data(data)
                .message(GlobalHttpCode.OK.getMessage())
                .build();
    }

    public static <T> BaseResponse<T> ok() {
        return BaseResponse.<T>builder()
                .success(true)
                .timestamp(LocalDateTime.now().toString())
                .status(GlobalHttpCode.OK.getHttpStatus().value())
                .code(GlobalHttpCode.OK.getCode())
                .message(GlobalHttpCode.OK.getMessage())
                .build();
    }
}
