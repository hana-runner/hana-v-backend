package com.v.hana.controller.interest;

import com.v.hana.auth.annotation.CurrentUser;
import com.v.hana.auth.util.user.SecurityUtil;
import com.v.hana.command.interest.GetUserInterestReportsCommand;
import com.v.hana.command.interest.GetUserInterestTransactionsCommand;
import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.interest.UserInterestReportsResponse;
import com.v.hana.dto.interest.UserInterestResponse;
import com.v.hana.dto.interest.UserInterestTransactionsResponse;
import com.v.hana.entity.user.User;
import com.v.hana.usecase.interest.UserInterestUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "UserInterestController", description = "사용자 관심사 컨트롤러")
@RestController
@RequestMapping("v1/api")
public class UserInterestController {
    private final UserInterestUseCase userInterestUseCase;
    private final SecurityUtil securityUtil;

    @MethodInfo(name = "getUserInterests", description = "사용자 관심사 목록 가져오기")
    @GetMapping("/user-interests/{userId}")
    @CurrentUser
    public ResponseEntity<UserInterestResponse> getUserInterests(@PathVariable Long userId) {
        User user = securityUtil.getCurrentUser();

        UserInterestResponse interests =
                userInterestUseCase.getUserInterests(
                        GetUserInterestsCommand.builder().userId(userId).build());
        return ResponseEntity.ok(interests);
    }

    @MethodInfo(name = "getUserInterests", description = "사용자 관심사별 거래 내역 가져오기")
    @GetMapping("/user-interests/transaction/{interestId}")
    @CurrentUser
    public ResponseEntity<UserInterestTransactionsResponse> getUserInterestTransaction(
            @PathVariable Long interestId,
            @RequestParam Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        User user = securityUtil.getCurrentUser();

        UserInterestTransactionsResponse transactions =
                userInterestUseCase.getUserInterestTransactions(
                        GetUserInterestTransactionsCommand.builder()
                                .interestId(interestId)
                                .userId(userId)
                                .year(year)
                                .month(month)
                                .build());
        return ResponseEntity.ok(transactions);
    }

    @MethodInfo(name = "getUserInterestReports", description = "사용자 관심사별 거래 내역 리포트를 가져옵니다.")
    @GetMapping("/user-interests/report/{interestId}")
    @CurrentUser
    public ResponseEntity<UserInterestReportsResponse> getUserInterestReports(
            @PathVariable Long interestId,
            @RequestParam Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        User user = securityUtil.getCurrentUser();

        UserInterestReportsResponse reports =
                userInterestUseCase.getUserInterestReports(
                        GetUserInterestReportsCommand.builder()
                                .interestId(interestId)
                                .userId(userId)
                                .year(year)
                                .month(month)
                                .build());
        return ResponseEntity.ok(reports);
    }

    public UserInterestController(
            UserInterestUseCase userInterestUseCase, SecurityUtil securityUtil) {
        this.userInterestUseCase = userInterestUseCase;
        this.securityUtil = securityUtil;
    }
}
