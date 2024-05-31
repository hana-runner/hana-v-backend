package com.v.hana.dto;

import com.v.hana.common.response.BaseResponse;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInterestResponse extends BaseResponse {

    private final ArrayList<UserInterestDto> data;

    @Builder
    public UserInterestResponse(ArrayList<UserInterestDto> data) {
        this.data = data;
    }
}
