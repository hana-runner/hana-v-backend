package com.v.hana.dto.account;

import com.v.hana.common.response.BaseResponse;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpenseResponse extends BaseResponse {
    private final ArrayList<ExpensePerInterest> data;

    @Builder
    public ExpenseResponse(ArrayList<ExpensePerInterest> data) {
        this.data = data;
    }
}
