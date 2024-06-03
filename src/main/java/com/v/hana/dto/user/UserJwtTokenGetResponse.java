package com.v.hana.dto.user;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserJwtTokenGetResponse extends BaseResponse {
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public UserJwtTokenGetResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
