package com.v.hana.dto.user;

import com.v.hana.common.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UserJoinRequest extends SelfValidating<UserJoinRequest> {
    @NotNull private final String username;
    @NotNull private final String pw;
    @NotNull private final String name;
    @NotNull private final String email;
    @NotNull private final int gender;

    @Builder
    public UserJoinRequest(String username, String pw, String name, String email, int gender) {
        this.username = username;
        this.name = name;
        this.pw = pw;
        this.email = email;
        this.gender = gender;
        this.validateSelf();
    }
}
