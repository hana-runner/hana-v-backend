package com.v.hana.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetSuccessResponse<T> extends BaseResponse {
    private T data;

    @Builder
    public GetSuccessResponse(T data) {
        this.data = data;
    }
}
