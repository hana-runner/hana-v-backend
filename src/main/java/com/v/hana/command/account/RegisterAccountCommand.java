package com.v.hana.command.account;

import com.v.hana.entity.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterAccountCommand {
    private final User user;
    private final String bankName;
    private final String accountNumber;
    private final Long balance;

    @Builder
    public RegisterAccountCommand(User user, String bankName, String accountNumber, Long balance) {
        this.user = user;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
