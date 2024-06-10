package com.v.hana.dto.interest;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UserCompareResponse extends BaseResponse {
    private final ArrayList<UserComparison> data;

    @Builder
    public UserCompareResponse(ArrayList<UserComparison> data) {
        this.data = data;
    }
}
