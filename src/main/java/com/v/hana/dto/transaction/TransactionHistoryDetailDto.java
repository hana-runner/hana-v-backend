package com.v.hana.dto.transaction;

import com.v.hana.dto.interest.InterestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionHistoryDetailDto {
    private final Long id;
    private final InterestDto interest;
    private final String description;
    private final Long amount;

    @Builder
    public TransactionHistoryDetailDto(
            Long id, InterestDto interest, String description, Long amount) {
        this.id = id;
        this.interest = interest;
        this.description = description;
        this.amount = amount;
    }
}
