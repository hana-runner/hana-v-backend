package com.v.hana.dto.account;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountCheckResponse extends BaseResponse {
    private final AccountDto data;

    @Builder
    public AccountCheckResponse(AccountDto data) {
        this.data = data;
    }
}
