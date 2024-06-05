package com.v.hana.dto.account;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountRegisterRequest {
    private final String bankName;
    private final String accountNumber;
    private final String accountName;
    private final String accountType;
    private final Long balance;

    @Builder
    public AccountRegisterRequest(
            String bankName,
            String accountNumber,
            String accountName,
            String accountType,
            Long balance) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
    }
}
