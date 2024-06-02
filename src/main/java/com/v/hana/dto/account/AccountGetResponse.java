package com.v.hana.dto.account;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class AccountGetResponse extends BaseResponse {
    private final ArrayList<AccountDto> data;

    @Builder
    public AccountGetResponse(ArrayList<AccountDto> data) {
        this.data = data;
    }

}
