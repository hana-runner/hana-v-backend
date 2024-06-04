package com.v.hana.event.account;

import com.v.hana.common.event.BaseEvent;
import com.v.hana.entity.account.Account;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAccountTransactionEvent extends BaseEvent {
    @NotNull(message = "계좌 정보는 필수입니다.")
    private final Account account;

    @NotNull(message = "옵션은 필수입니다.")
    private final Integer option;

    @NotNull(message = "정렬 여부는 필수입니다.")
    private final Boolean sort;

    @NotNull(message = "시작 날짜는 필수입니다.")
    private final LocalDateTime start;

    @NotNull(message = "종료 날짜는 필수입니다.")
    private final LocalDateTime end;

    @Builder
    public ReadAccountTransactionEvent(
            Account account, Integer option, Boolean sort, LocalDateTime start, LocalDateTime end) {
        super();
        this.account = account;
        this.option = option;
        this.sort = sort;
        this.start = start;
        this.end = end;
    }
}
