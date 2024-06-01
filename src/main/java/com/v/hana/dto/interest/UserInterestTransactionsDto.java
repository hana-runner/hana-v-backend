package com.v.hana.dto.interest;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UserInterestTransactionsDto {
    private final ArrayList<UserInterestTransactionDto> transaction;
    private final int totalPrice;

    @Builder
    public UserInterestTransactionsDto(ArrayList<UserInterestTransactionDto> transaction, int totalPrice) {
        this.transaction = transaction;
        this.totalPrice = totalPrice;
    }
}
