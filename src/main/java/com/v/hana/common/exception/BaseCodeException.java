package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseCodeException extends RuntimeException {
    private final BaseHttpCode baseHttpCode;

    @Builder
    public BaseCodeException(BaseHttpCode baseHttpCode) {
        super(baseHttpCode.getHttpReason().getMessage());
        this.baseHttpCode = baseHttpCode;
    }
}
