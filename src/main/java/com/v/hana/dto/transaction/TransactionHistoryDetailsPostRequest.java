package com.v.hana.dto.transaction;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransactionHistoryDetailsPostRequest {
    private ArrayList<InterestDto> interests;

    @Builder
    public TransactionHistoryDetailsPostRequest(ArrayList<InterestDto> interests) {
        this.interests = interests;
    }
}
