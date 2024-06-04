package com.v.hana.dto.transaction;

import com.v.hana.dto.interest.InterestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionHistoryDetailDto {
    private final InterestDto interest;
    private final String description;
    private final Long amount;

    @Builder
    public TransactionHistoryDetailDto(InterestDto interest, String description, Long amount) {
        this.interest = interest;
        this.description = description;
        this.amount = amount;
    }
}
