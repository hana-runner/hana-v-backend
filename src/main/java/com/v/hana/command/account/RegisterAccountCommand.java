package com.v.hana.command.account;

import com.v.hana.entity.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterAccountCommand {
    private final User user;
    private final String bankName;
    private final String accountNumber;
    private final String accountName;
    private final String accountType;
    private final Long balance;

    @Builder
    public RegisterAccountCommand(User user, String bankName, String accountNumber, String accountName, String accountType, Long balance) {
        this.user = user;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
    }
}
