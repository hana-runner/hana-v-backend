package com.v.hana.dto.example;

import com.v.hana.common.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ExampleRequest extends SelfValidating<ExampleRequest> {
    @NotNull(message = "request는 빈 값일 수 없습니다.")
    private final int request;

    @Builder
    public ExampleRequest(int request) {
        this.request = request;
        this.validateSelf();
    }
}
