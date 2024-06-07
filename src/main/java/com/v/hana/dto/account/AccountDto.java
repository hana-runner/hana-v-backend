package com.v.hana.dto.account;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountDto {
    private final Long id;
    private final String bankName;
    private final String accountNumber;
    private final Long balance;
    private final String accountName;
    private final String accountType;

    @Builder
    public AccountDto(Long id, String bankName, String accountNumber, Long balance, String accountName, String accountType) {
        this.id = id;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountName = accountName;
        this.accountType = accountType;
    }
}
