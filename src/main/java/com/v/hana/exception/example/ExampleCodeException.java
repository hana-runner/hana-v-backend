package com.v.hana.exception.example;

import com.v.hana.common.exception.BaseCodeException;
import com.v.hana.common.exception.ExampleHttpCode;

public class ExampleCodeException extends BaseCodeException {

    public ExampleCodeException() {
        super(ExampleHttpCode.EXAMPLE_IS_0);
    }
}
