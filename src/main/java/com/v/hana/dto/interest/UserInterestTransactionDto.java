package com.v.hana.dto.interest;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserInterestTransactionDto {
    private final String description, bankName, accountNumber;
    private final Long interestId, transactionHistoryId, amount;
    private final LocalDateTime createdAt;

    @Builder
    public UserInterestTransactionDto(String description, String bankName, String accountNumber, Long interestId, Long transactionHistoryId, Long amount, LocalDateTime createdAt) {
        this.description = description;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.interestId = interestId;
        this.transactionHistoryId = transactionHistoryId;
        this.amount = amount;
        this.createdAt = createdAt;
    }
}
