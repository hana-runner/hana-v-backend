package com.v.hana.common.event;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public abstract class BaseEvent {
    @NotNull private final String id; // UUID
    @NotNull private final String name; // 클래스 이름
    @NotNull private final LocalDateTime localDateTime; // 이벤트 발생 시간

    public BaseEvent() {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = this.getClass().getSimpleName();
        this.localDateTime = LocalDateTime.now();
    }
}
