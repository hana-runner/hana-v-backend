package com.v.hana.dto;

import com.v.hana.common.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UserInterestResponse extends BaseResponse {

    private final ArrayList<UserInterestDto> data;

    @Builder
    public UserInterestResponse(ArrayList<UserInterestDto> data) {
        this.data = data;
    }
}
