package com.v.hana.dto.interest;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInterestReportsDto {

    private final ArrayList<Long> myMonth;
    private final Long myAverage;
    private final Long peerAverage;

    @Builder
    public UserInterestReportsDto(ArrayList<Long> myMonth, Long myAverage, Long peerAverage) {
        this.myMonth = myMonth;
        this.myAverage = myAverage;
        this.peerAverage = peerAverage;
    }
}
