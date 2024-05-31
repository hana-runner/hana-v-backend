package com.v.hana.controller;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.UserInterestResponse;
import com.v.hana.service.interest.UserInterestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "UserInterestController", description = "사용자 관심사 컨트롤러")
@RestController
@RequestMapping("v1/api")
public class UserInterestController {
    private final UserInterestService userInterestService;
    @MethodInfo(name = "getUserInterests", description = "사용자 관심사 목록 가져오기")
    @GetMapping("/user-interests/{userId}")
    public ResponseEntity<UserInterestResponse> getUserInterests(
            @PathVariable Long userId) {
        UserInterestResponse interests = userInterestService.getUserInterests(userId);
        return ResponseEntity.ok(interests);
    }

    public UserInterestController(UserInterestService userInterestService) {
        this.userInterestService = userInterestService;
    }
}
