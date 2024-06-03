package com.v.hana.dto.interest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InterestDto {
    private final Long interestId;
    private final String title, description, color;

    @Builder
    public InterestDto(Long interestId, String title, String description, String color) {
        this.interestId = interestId;
        this.title = title;
        this.description = description;
        this.color = color;
    }
}
