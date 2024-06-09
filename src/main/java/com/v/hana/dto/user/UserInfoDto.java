package com.v.hana.dto.user;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {
    private String name;
    private String username;
    private String email;
    private LocalDate birthday;
    private int gender;
}
