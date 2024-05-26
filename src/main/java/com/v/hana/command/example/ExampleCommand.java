package com.v.hana.command.example;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExampleCommand {
    private final int request;

    @Builder
    public ExampleCommand(int request) {
        this.request = request;
    }
}
