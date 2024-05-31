package com.v.hana.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInterestDto {
    private final String title, subtitle, imageUrl, color;

    @Builder
    public UserInterestDto(String title, String subtitle, String imageUrl, String color) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
        this.color = color;
    }
}
