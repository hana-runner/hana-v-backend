package com.v.hana.dto.user;

import lombok.Getter;

@Getter
public class UserChangePwRequest {
    private String email;
    private String pw;
}
