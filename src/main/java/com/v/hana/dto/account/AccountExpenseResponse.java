package com.v.hana.dto.account;

import com.v.hana.common.response.BaseResponse;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountExpenseResponse extends BaseResponse {

    private final ArrayList<ExpensePerCategory> data;

    @Builder
    public AccountExpenseResponse(ArrayList<ExpensePerCategory> data) {
        this.data = data;
    }
}
