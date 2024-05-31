package com.v.hana.dto.example;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExampleRequest {
    @Min(value = 0, message = "request는 1 이상이어야 합니다.")
    @Max(value = 100, message = "request는 100 이하이어야 합니다.")
    private final int request;

    @Builder
    public ExampleRequest(int request) {
        this.request = request;
    }
}
