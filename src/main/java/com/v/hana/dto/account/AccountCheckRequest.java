package com.v.hana.dto.account;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountCheckRequest {
    private final String bankName;
    private final String accountNumber;

    @Builder
    public AccountCheckRequest(String bankName, String accountNumber) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

}
