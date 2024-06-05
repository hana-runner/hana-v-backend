package com.v.hana.usecase.account;

import com.v.hana.command.account.*;
import com.v.hana.dto.account.*;

public interface AccountUseCase {
    AccountGetResponse getAccounts(GetAccountsCommand command);

    AccountTransactionGetResponse readTransactionHistories(ReadTransactionsCommand command);

    AccountCheckResponse checkAccountNumber(CheckAccountNumberCommand command);

    AccountRegisterResponse registerAccount(RegisterAccountCommand command);

    AccountExpenseResponse getExpensePerCategories(GetExpenseCommand command);
}
