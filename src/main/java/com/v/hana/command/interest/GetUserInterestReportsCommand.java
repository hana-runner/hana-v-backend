package com.v.hana.command.interest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserInterestReportsCommand {
    private final Long interestId;
    private final Long userId;

    @Builder
    public GetUserInterestReportsCommand(Long interestId, Long userId) {
        this.interestId = interestId;
        this.userId = userId;
    }
}
