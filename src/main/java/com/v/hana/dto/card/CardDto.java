package com.v.hana.dto.card;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CardDto {
    private final Long id;
    private final String name;
    private final String image;

    @Builder
    public CardDto(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
