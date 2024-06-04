package com.v.hana.usecase.interest;

import com.v.hana.dto.interest.InterestsResponse;
import com.v.hana.entity.interest.Interest;
import java.util.ArrayList;

public interface InterestUseCase {
    InterestsResponse getInterests();

    ArrayList<Interest> getInterestsById(ArrayList<Long> ids);
}
