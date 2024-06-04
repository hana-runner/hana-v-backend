package com.v.hana.event.transaction;

import com.v.hana.common.event.BaseEvent;
import com.v.hana.dto.transaction.InterestDto;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadInterestsEvent extends BaseEvent {
    @NotNull(message = "관심사 목록은 필수입니다.")
    private final ArrayList<InterestDto> interests;

    @Builder
    public ReadInterestsEvent(ArrayList<InterestDto> interests) {
        this.interests = interests;
    }
}
