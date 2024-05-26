package com.v.hana.dto.example;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExampleRequestDto {
    private final String request;

    @Builder
    public ExampleRequestDto(String request) {
        this.request = request;
    }
}
