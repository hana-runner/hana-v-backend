package com.v.hana.dto.card;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CardBenefitDto {
    private String title;
    private String description;
    private String group;

    @Builder
    public CardBenefitDto(String title, String description, String group) {
        this.title = title;
        this.description = description;
        this.group = group;
    }
}
