package com.v.hana.dto.interest;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UserInterestReportsResponse extends BaseResponse {

        private final ArrayList<Long> myMouth;
        private final Long myAverage;
        private final Long peerAverage;

        @Builder
        public UserInterestReportsResponse(ArrayList<Long> myMouth, Long myAverage, Long peerAverage) {
                this.myMouth = myMouth;
                this.myAverage = myAverage;
                this.peerAverage = peerAverage;
        }
}
