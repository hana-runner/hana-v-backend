package com.v.hana.dto.account;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountRegisterResponse extends BaseResponse {
    private final Long id;

    @Builder
    public AccountRegisterResponse(Long id) {
        this.id = id;
    }
}
