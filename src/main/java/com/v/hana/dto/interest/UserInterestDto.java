package com.v.hana.dto.interest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInterestDto {
    private final Long interestId;
    private final String title, subtitle, imageUrl, color;

    @Builder
    public UserInterestDto(
            Long interestId, String title, String subtitle, String imageUrl, String color) {
        this.interestId = interestId;
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
        this.color = color;
    }
}
