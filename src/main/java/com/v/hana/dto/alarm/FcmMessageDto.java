package com.v.hana.dto.alarm;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 실제로 fcm에 전달될 객체
@Getter
@Builder
public class FcmMessageDto {
    private boolean validateOnly;
    private FcmMessageDto.Message message;

    @Builder
    @RequiredArgsConstructor
    @Getter
    public static class Message {
        private final FcmMessageDto.Notification notification;
        private final String token;
    }

    @Builder
    @RequiredArgsConstructor
    @Getter
    public static class Notification {
        private final String title;
        private final String body;
    }
}
