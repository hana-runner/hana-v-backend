package com.v.hana.dto.account;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountCheckResponse extends BaseResponse {
    private final String bankName;
    private final String accountNumber;
    private final Long balance;
    private final String accountType;
    private final String accountName;

    @Builder
    public AccountCheckResponse(String bankName, String accountNumber, Long balance, String accountType, String accountName) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.accountName = accountName;
    }
}
