package com.v.hana.usecase.account;

import com.v.hana.command.account.GetAccountsCommand;
import com.v.hana.dto.account.AccountGetResponse;

public interface AccountUseCase {
     AccountGetResponse getAccounts(GetAccountsCommand command);
}
