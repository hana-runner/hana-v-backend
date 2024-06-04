package com.v.hana.command.interest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserInterestTransactionsCommand {
    private final Long interestId, userId;
    private final int year, month;

    @Builder
    public GetUserInterestTransactionsCommand(Long interestId, Long userId, int year, int month) {
        this.interestId = interestId;
        this.userId = userId;
        this.year = year;
        this.month = month;
    }
}
