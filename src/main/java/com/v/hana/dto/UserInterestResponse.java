package com.v.hana.dto;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInterestResponse extends BaseResponse {
    private final String title, subtitle, imageUrl, color;

    @Builder
    public UserInterestResponse(String title, String subtitle, String imageUrl, String color) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
        this.color = color;
    }
}
