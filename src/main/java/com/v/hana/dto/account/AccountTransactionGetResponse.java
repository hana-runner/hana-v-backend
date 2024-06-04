package com.v.hana.dto.account;

import com.v.hana.common.response.BaseResponse;
import com.v.hana.dto.transaction.TransactionHistoryDto;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountTransactionGetResponse extends BaseResponse {
    private final ArrayList<TransactionHistoryDto> data;

    @Builder
    public AccountTransactionGetResponse(ArrayList<TransactionHistoryDto> data) {
        this.data = data;
    }
}
