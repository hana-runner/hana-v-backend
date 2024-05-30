package com.v.hana.common.exception;

import com.v.hana.common.response.BaseHttpCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public abstract class BaseCodeException extends RuntimeException {
    private final BaseHttpCode baseHttpCode;

    protected BaseCodeException(BaseHttpCode baseHttpCode) {
        super(baseHttpCode.getHttpReason().getMessage());
        this.baseHttpCode = baseHttpCode;
    }
}
