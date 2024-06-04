package com.v.hana.dto.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterestDto {
    private Long id;
    private String description;
    private Long amount;

    @Builder
    public InterestDto(Long id, String description, Long amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }
}
