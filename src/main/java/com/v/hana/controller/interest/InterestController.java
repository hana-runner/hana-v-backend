package com.v.hana.controller.interest;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.interest.InterestsResponse;
import com.v.hana.usecase.interest.InterestUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TypeInfo(name = "InterestController", description = "관심사 컨트롤러")
@RestController
@RequestMapping("v1/api")
public class InterestController {

    private final InterestUseCase interestUseCase;

    @MethodInfo(name = "getInterests", description = "관심사 목록 가져오기")
    @GetMapping("/interests")
    public ResponseEntity<InterestsResponse> getInterests() {
        InterestsResponse interests = interestUseCase.getInterests();
        return ResponseEntity.ok(interests);
    }

    public InterestController(InterestUseCase interestUseCase) {
        this.interestUseCase = interestUseCase;
    }
}
