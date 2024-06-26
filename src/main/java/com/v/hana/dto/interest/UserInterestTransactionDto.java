package com.v.hana.dto.interest;

import java.time.LocalDateTime;

public interface UserInterestTransactionDto {
    Long getTransactionHistoryId();

    Long getInterestId();

    Long getAmount();

    String getDescription();

    String getBankName();

    String getAccountNumber();

    LocalDateTime getCreatedAt();
}
