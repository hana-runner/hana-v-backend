package com.v.hana.dto.interest;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UserInterestTransactionsResponse {
    private final UserInterestTransactionsDto data;

    @Builder
    public UserInterestTransactionsResponse(UserInterestTransactionsDto data) {
        this.data = data;
    }
}
