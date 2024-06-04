package com.v.hana.usecase.account;

import com.v.hana.command.account.CheckAccountNumberCommand;
import com.v.hana.command.account.GetAccountsCommand;
import com.v.hana.command.account.ReadTransactionsCommand;
import com.v.hana.dto.account.AccountGetResponse;
import com.v.hana.dto.account.AccountTransactionGetResponse;
import com.v.hana.entity.account.AccountApi;

public interface AccountUseCase {
    AccountGetResponse getAccounts(GetAccountsCommand command);

    AccountTransactionGetResponse readTransactionHistories(ReadTransactionsCommand command);


    AccountApi checkAccountNumber(CheckAccountNumberCommand command);
}
