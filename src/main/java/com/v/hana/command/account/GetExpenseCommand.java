package com.v.hana.command.account;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetExpenseCommand {
    private final Long userId;
    private final LocalDate start;
    private final LocalDate end;

    @Builder
    public GetExpenseCommand(Long userId, LocalDate start, LocalDate end) {
        this.userId = userId;
        this.start = start;
        this.end = end;
    }
}
