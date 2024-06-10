package com.v.hana.dto.interest;

import com.v.hana.common.response.BaseResponse;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCompareResponse extends BaseResponse {
    private final ArrayList<UserComparison> data;

    @Builder
    public UserCompareResponse(ArrayList<UserComparison> data) {
        this.data = data;
    }
}
