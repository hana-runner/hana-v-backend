package com.v.hana.dto.card;

import com.v.hana.common.response.BaseResponse;
import com.v.hana.entity.card.Card;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CardInterestResponse extends BaseResponse {
    private final ArrayList<Card> data;

    @Builder
    public CardInterestResponse(ArrayList<Card> data) {
        this.data = data;
    }
}
