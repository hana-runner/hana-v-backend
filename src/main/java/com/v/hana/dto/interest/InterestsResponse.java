package com.v.hana.dto.interest;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class InterestsResponse {
    private final ArrayList<InterestDto> data;

    @Builder
    public InterestsResponse(ArrayList<InterestDto> data) {
        this.data = data;
    }
}
