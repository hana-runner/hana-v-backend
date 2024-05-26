package com.v.hana.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public abstract class BaseResponse {
    private final boolean success;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final String timestamp;

    private final int status;
    private final String code;
    private final String message;

    public BaseResponse() {
        this.success = true;
        this.timestamp =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = GlobalHttpCode.OK.getHttpStatus().value();
        this.code = GlobalHttpCode.OK.getCode();
        this.message = GlobalHttpCode.OK.getMessage();
    }
}
