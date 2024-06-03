package com.v.hana.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UserLoginRequest {
    @NotNull private final String username;
    @NotNull private final String pw;

    @Builder
    public UserLoginRequest(String username, String pw) {
        this.username = username;
        this.pw = pw;
    }
}
