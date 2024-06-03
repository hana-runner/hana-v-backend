package com.v.hana.dto.interest;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UserInterestTransactionsResponse extends BaseResponse {
    private final UserInterestTransactionsDto data;

    @Builder
    public UserInterestTransactionsResponse(UserInterestTransactionsDto data) {
        this.data = data;
    }
}
