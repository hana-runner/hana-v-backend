package com.v.hana.dto.interest;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class InterestsResponse {
    private final ArrayList<InterestDto> data;

    @Builder
    public InterestsResponse(ArrayList<InterestDto> data) {
        this.data = data;
    }
}
