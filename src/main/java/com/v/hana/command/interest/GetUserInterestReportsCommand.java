package com.v.hana.command.interest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserInterestReportsCommand {
    private final Long interestId;
    private final Long userId;
    private final int year;
    private final int month;

    @Builder
    public GetUserInterestReportsCommand(Long interestId, Long userId, int year, int month) {
        this.interestId = interestId;
        this.userId = userId;
        this.year = year;
        this.month = month;
    }
}
