package com.v.hana.event.interest;

import com.v.hana.common.event.BaseEvent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SumAmountEvent extends BaseEvent {
    @NotNull(message = "관심사 ID는 필수입니다.")
    private final Long interestId;

    @NotNull(message = "유저 ID는 필수입니다.")
    private final Long userId;

    @NotNull(message = "년도는 필수입니다.")
    private final int year;

    @NotNull(message = "월은 필수입니다.")
    private final int month;

    @Builder
    public SumAmountEvent(Long interestId, Long userId, int year, int month) {
        this.interestId = interestId;
        this.userId = userId;
        this.year = year;
        this.month = month;
    }
}
