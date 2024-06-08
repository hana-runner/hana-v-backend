package com.v.hana.dto.user;

import com.v.hana.common.response.BaseResponse;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserGetResponse extends BaseResponse {
    private final UserInfoDto data;

    @Builder
    public UserGetResponse(
            String username, String email, LocalDate birthday, int gender, UserInfoDto data) {
        this.data = data;
    }
}
