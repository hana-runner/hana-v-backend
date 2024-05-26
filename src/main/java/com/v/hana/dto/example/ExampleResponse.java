package com.v.hana.dto.example;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExampleResponse extends BaseResponse {
    private final int response;

    @Builder
    public ExampleResponse(int response) {
        this.response = response;
    }
}
