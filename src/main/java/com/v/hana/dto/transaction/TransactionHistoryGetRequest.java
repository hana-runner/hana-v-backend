package com.v.hana.dto.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionHistoryGetRequest {
    @NotNull(message = "accountId는 필수 값입니다.")
    private final Long accountId;

    @Builder
    public TransactionHistoryGetRequest(Long accountId) {
        this.accountId = accountId;
    }
}
