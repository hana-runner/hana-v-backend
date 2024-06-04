package com.v.hana.command.account;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadTransactionsCommand {
    private final Long accountId;
    private final Integer option;
    private final Boolean sort;
    private final LocalDateTime start;
    private final LocalDateTime end;

    @Builder
    public ReadTransactionsCommand(
            Long accountId, Integer option, Boolean sort, LocalDateTime start, LocalDateTime end) {
        this.accountId = accountId;
        this.option = option;
        this.sort = sort;
        this.start = start;
        this.end = end;
    }
}
