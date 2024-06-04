package com.v.hana.dto.account;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountRegisterRequest {
    private final String bankName;
    private final String accountNumber;
    private final Long balance;

    @Builder
    public AccountRegisterRequest(String bankName, String accountNumber, Long balance) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
