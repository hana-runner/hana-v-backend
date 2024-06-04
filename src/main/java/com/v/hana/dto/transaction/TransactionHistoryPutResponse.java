package com.v.hana.dto.transaction;

import com.v.hana.common.response.BaseResponse;
import com.v.hana.dto.interest.InterestDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionHistoryPutResponse extends BaseResponse {
    private final Long id; // transactionHistoryId
    private final Long accountId;
    private final String categoryTitle;
    private final String categoryColor;
    private final Integer approvalNumber;
    private final String type;
    private final String description;
    private final String action;
    private final Long amount;
    private final Long balance;
    private final LocalDateTime createdAt;
    private final ArrayList<InterestDto> interests;

    @Builder
    public TransactionHistoryPutResponse(
            Long id,
            Long accountId,
            String categoryTitle,
            String categoryColor,
            Integer approvalNumber,
            String type,
            String description,
            String action,
            Long amount,
            Long balance,
            LocalDateTime createdAt,
            ArrayList<InterestDto> interests) {
        this.id = id;
        this.accountId = accountId;
        this.categoryTitle = categoryTitle;
        this.categoryColor = categoryColor;
        this.approvalNumber = approvalNumber;
        this.type = type;
        this.description = description;
        this.action = action;
        this.amount = amount;
        this.balance = balance;
        this.createdAt = createdAt;
        this.interests = interests;
    }
}
