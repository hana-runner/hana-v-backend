package com.v.hana.dto.transaction;

import jakarta.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionHistoryPutRequest {
    @NotNull(message = "categoryId는 필수 입력 값입니다.")
    private final Long categoryId;

    @ConstructorProperties({"categoryId"})
    @Builder
    public TransactionHistoryPutRequest(Long categoryId) {
        this.categoryId = categoryId;
    }
}
