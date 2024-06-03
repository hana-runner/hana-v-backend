package com.v.hana.usecase.interest;

import com.v.hana.command.interest.GetInterestsCommand;
import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.dto.interest.InterestsResponse;

public interface InterestUseCase {
    public InterestsResponse getInterests();
}
