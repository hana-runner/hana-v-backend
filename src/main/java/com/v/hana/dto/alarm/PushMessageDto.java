package com.v.hana.dto.alarm;

import lombok.Getter;

@Getter
public class PushMessageDto {
    private long user_id;
    private String title;
    private String message;
}
