package com.v.hana.dto.interest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddUserInterestRequest {
    private final Long interestId;
    private final String title, description, imageUrl;

    @Builder
    public AddUserInterestRequest(Long interestId, String title, String description, String imageUrl) {
        this.interestId = interestId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
