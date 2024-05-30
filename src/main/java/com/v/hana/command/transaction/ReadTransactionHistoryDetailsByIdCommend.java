package com.v.hana.command.transaction;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadTransactionHistoryDetailsByIdCommend {
    private final Long id;

    @Builder
    public ReadTransactionHistoryDetailsByIdCommend(Long id) {
        this.id = id;
    }
}
