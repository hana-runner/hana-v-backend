package com.v.hana.command.account;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckAccountNumberCommand {
    private final String bankName;
    private final String accountNumber;

    @Builder
    public CheckAccountNumberCommand(String bankName, String accountNumber) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }
}
