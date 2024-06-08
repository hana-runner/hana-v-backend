package com.v.hana.dto.interest;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AddUserInterestRequest {
    private final Long interestId;
    private final String title, description;
    private final MultipartFile image;

    @Builder
    public AddUserInterestRequest(Long interestId, String title, String description, MultipartFile image) {
        this.interestId = interestId;
        this.title = title;
        this.description = description;
        this.image = image;
    }
}
