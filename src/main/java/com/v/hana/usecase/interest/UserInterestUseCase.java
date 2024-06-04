package com.v.hana.usecase.interest;

import com.v.hana.command.interest.GetUserInterestReportsCommand;
import com.v.hana.command.interest.GetUserInterestTransactionsCommand;
import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.dto.interest.UserInterestReportsResponse;
import com.v.hana.dto.interest.UserInterestResponse;
import com.v.hana.dto.interest.UserInterestTransactionsResponse;

public interface UserInterestUseCase {
    UserInterestResponse getUserInterests(GetUserInterestsCommand command);

    UserInterestTransactionsResponse getUserInterestTransactions(
            GetUserInterestTransactionsCommand command);

    UserInterestReportsResponse getUserInterestReports(GetUserInterestReportsCommand command);
}
