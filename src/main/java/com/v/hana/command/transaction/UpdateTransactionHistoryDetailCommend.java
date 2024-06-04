package com.v.hana.command.transaction;

import com.v.hana.dto.transaction.InterestDto;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateTransactionHistoryDetailCommend {
    private final Long id;
    private final ArrayList<InterestDto> interests;

    @Builder
    public UpdateTransactionHistoryDetailCommend(Long id, ArrayList<InterestDto> interests) {
        this.id = id;
        this.interests = interests;
    }
}
