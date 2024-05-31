package com.v.hana.command.interest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserInterestsCommand {
    private final Long userId;

    @Builder
    public GetUserInterestsCommand(Long userId) {
        this.userId = userId;
    }
}
