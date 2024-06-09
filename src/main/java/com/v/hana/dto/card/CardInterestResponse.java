package com.v.hana.dto.card;

import com.v.hana.entity.card.CardInterest;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CardInterestResponse {
    private final ArrayList<CardInterest> data;

    @Builder
    public CardInterestResponse(ArrayList<CardInterest> data) {
        this.data = data;
    }
}
