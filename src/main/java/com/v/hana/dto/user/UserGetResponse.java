package com.v.hana.dto.user;

import com.v.hana.common.constant.Gender;
import com.v.hana.common.response.BaseResponse;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserGetResponse extends BaseResponse {
    private String username;
    private String email;
    private LocalDate birthday;
    private Gender gender;

    @Builder
    public UserGetResponse(String username, String email, LocalDate birthday, Gender gender) {
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
    }
}
