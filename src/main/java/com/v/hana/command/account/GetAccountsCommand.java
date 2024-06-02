package com.v.hana.command.account;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAccountsCommand {
    private final Long userId;

    @Builder
    public GetAccountsCommand(Long userId) {
        this.userId = userId;}
}
