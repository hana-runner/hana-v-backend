package com.v.hana.usecase.interest;

import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.dto.UserInterestResponse;

public interface UserInterestUseCase {
    UserInterestResponse getUserInterests(GetUserInterestsCommand command);
}
