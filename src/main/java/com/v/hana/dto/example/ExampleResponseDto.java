package com.v.hana.dto.example;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExampleResponseDto {
    private final String response;

    @Builder
    public ExampleResponseDto(String response) {
        this.response = response;
    }
}
