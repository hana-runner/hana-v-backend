package com.v.hana.dto.account;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class AccountExpenseResponse extends BaseResponse {

    private final ArrayList<ExpensePerCategory> data;

    @Builder
    public AccountExpenseResponse(ArrayList<ExpensePerCategory> data) {
        this.data = data;
    }

}
