package com.v.hana.dto.card;

import com.v.hana.common.response.BaseResponse;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CardInterestResponse extends BaseResponse {
    private final ArrayList<CardDto> data;

    @Builder
    public CardInterestResponse(ArrayList<CardDto> data) {
        this.data = data;
    }
}
