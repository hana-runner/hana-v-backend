package com.v.hana.usecase.interest;

import com.v.hana.command.interest.AddUserInterestCommand;
import com.v.hana.command.interest.GetUserInterestReportsCommand;
import com.v.hana.command.interest.GetUserInterestTransactionsCommand;
import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.command.interest.ModifyUserInterestCommand;
import com.v.hana.common.constant.Gender;
import com.v.hana.common.response.PostSuccessResponse;
import com.v.hana.common.response.PutSuccessResponse;
import com.v.hana.dto.interest.UserCompareResponse;
import com.v.hana.dto.interest.UserInterestReportsResponse;
import com.v.hana.dto.interest.UserInterestResponse;
import com.v.hana.dto.interest.UserInterestTransactionsResponse;

public interface UserInterestUseCase {
    UserInterestResponse getUserInterests(GetUserInterestsCommand command);

    UserInterestTransactionsResponse getUserInterestTransactions(
            GetUserInterestTransactionsCommand command);

    UserInterestReportsResponse getUserInterestReports(GetUserInterestReportsCommand command);

    PostSuccessResponse addUserInterest(AddUserInterestCommand command);

    PutSuccessResponse modifyUserInterest(ModifyUserInterestCommand command);

<<<<<<< Updated upstream
    UserCompareResponse getComparison(Long userId, Long interestId, int age, int year, int month);
=======
    UserCompareResponse getComparison(
            Long userId, Long interestId, int age, int year, int month, Gender gender);
>>>>>>> Stashed changes
}
