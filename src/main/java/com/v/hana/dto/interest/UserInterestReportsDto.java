package com.v.hana.dto.interest;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInterestReportsDto {

    private final ArrayList<Long> myMouth;
    private final Long myAverage;
    private final Long peerAverage;

    @Builder
    public UserInterestReportsDto(ArrayList<Long> myMouth, Long myAverage, Long peerAverage) {
        this.myMouth = myMouth;
        this.myAverage = myAverage;
        this.peerAverage = peerAverage;
    }
}
