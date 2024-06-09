package com.v.hana.dto.interest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyMouthDto {
    private final int year;
    private final int month;
    private final Long amount;

    @Builder
    public MyMouthDto(int year, int month, Long amount) {
        this.year = year;
        this.month = month;
        this.amount = amount;
    }
}
