package com.v.hana.dto.interest;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInterestTransactionsDto {
    private final ArrayList<UserInterestTransactionDto> transaction;
    private final Long totalSpent;
    private final int interestTotalSpent;
    private final String interestTitle, color;

    @Builder
    public UserInterestTransactionsDto(
            ArrayList<UserInterestTransactionDto> transaction,
            Long totalSpent,
            int interestTotalSpent,
            String title,
            String color) {
        this.transaction = transaction;
        this.totalSpent = totalSpent;
        this.interestTotalSpent = interestTotalSpent;
        this.interestTitle = title;
        this.color = color;
    }
}
