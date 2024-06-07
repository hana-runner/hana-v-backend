package com.v.hana.dto.account;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ExpenseResponse extends BaseResponse {
    private final ArrayList<ExpensePerInterest> data;

    @Builder
    public ExpenseResponse(ArrayList<ExpensePerInterest> data) {
        this.data = data;
    }
}
