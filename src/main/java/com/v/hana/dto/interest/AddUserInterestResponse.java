package com.v.hana.dto.interest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddUserInterestResponse {
    private final Long id, interestId;
    private final String title, description, imageUrl;

    @Builder
    public AddUserInterestResponse(Long id, Long interestId, String title, String description, String imageUrl) {
        this.id = id;
        this.interestId = interestId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
