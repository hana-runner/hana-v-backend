package com.v.hana.command.card;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserInterestCardsCommand {
    private final Long interestId;

    @Builder
    public GetUserInterestCardsCommand(Long interestId) {
        this.interestId = interestId;
    }
}
