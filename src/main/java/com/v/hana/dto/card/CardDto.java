package com.v.hana.dto.card;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CardDto {
    private final Long id;
    private final String name;
    private final String image;
    private final ArrayList<CardBenefitDto> cardBenefits;

    @Builder
    public CardDto(Long id, String name, String image, ArrayList<CardBenefitDto> cardBenefits) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.cardBenefits = cardBenefits;
    }
}
