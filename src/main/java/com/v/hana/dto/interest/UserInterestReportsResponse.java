package com.v.hana.dto.interest;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInterestReportsResponse extends BaseResponse {

    private final UserInterestReportsDto data;

    @Builder
    public UserInterestReportsResponse(UserInterestReportsDto data) {
        this.data = data;
    }
}
