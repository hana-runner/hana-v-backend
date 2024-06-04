package com.v.hana.command.transaction;

import com.v.hana.entity.account.Account;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadTransactionsCommand {
    private final Account account;
    private final Integer option;
    private final Boolean sort;
    private final LocalDateTime start;
    private final LocalDateTime end;

    @Builder
    public ReadTransactionsCommand(
            Account account, Integer option, Boolean sort, LocalDateTime start, LocalDateTime end) {
        this.account = account;
        this.option = option;
        this.sort = sort;
        this.start = start;
        this.end = end;
    }
}
