package com.v.hana.command.transaction;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadTransactionHistoryByIdCommend {
    private final Long id;

    @Builder
    public ReadTransactionHistoryByIdCommend(Long id) {
        this.id = id;
    }
}
