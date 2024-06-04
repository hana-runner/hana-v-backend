package com.v.hana.dto.account;

import com.v.hana.common.response.BaseResponse;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountGetResponse extends BaseResponse {
    private final ArrayList<AccountDto> data;

    @Builder
    public AccountGetResponse(ArrayList<AccountDto> data) {
        this.data = data;
    }
}
