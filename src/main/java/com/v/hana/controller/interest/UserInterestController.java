package com.v.hana.controller.interest;

import com.v.hana.auth.annotation.CurrentUser;
import com.v.hana.auth.util.user.SecurityUtil;
import com.v.hana.command.interest.GetUserInterestReportsCommand;
import com.v.hana.command.interest.GetUserInterestTransactionsCommand;
import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.exception.BaseExceptionResponse;
import com.v.hana.dto.interest.UserInterestReportsResponse;
import com.v.hana.dto.interest.UserInterestResponse;
import com.v.hana.dto.interest.UserInterestTransactionsResponse;
import com.v.hana.entity.user.User;
import com.v.hana.usecase.interest.UserInterestUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "UserInterestController", description = "사용자 관심사 컨트롤러")
@RestController
@Tag(name = "UserInterest", description = "사용자 관심사")
@RequestMapping("v1/api")
public class UserInterestController {
    private final UserInterestUseCase userInterestUseCase;
    private final SecurityUtil securityUtil;

    @MethodInfo(name = "getUserInterests", description = "사용자 관심사 목록 가져오기")
    @Operation(
            summary = "사용자 관심사 목록 가져오기",
            description = "사용자의 관심사 목록을 가져옵니다.",
            parameters = {@Parameter(name = "userId", description = "사용자 ID", required = true)},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "사용자 관심사 목록 가져오기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                UserInterestResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "사용자 관심사 목록 가져오기 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
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
    @Operation(
            summary = "사용자 관심사별 거래 내역 가져오기",
            description = "사용자의 관심사별 거래 내역을 가져옵니다.",
            parameters = {
                @Parameter(name = "interestId", description = "관심사 ID", required = true),
                @Parameter(name = "userId", description = "사용자 ID", required = true),
                @Parameter(name = "year", description = "년도", required = true),
                @Parameter(name = "month", description = "월", required = true)
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "사용자 관심사별 거래 내역 가져오기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                UserInterestTransactionsResponse
                                                                        .class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "사용자 관심사별 거래 내역 가져오기 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
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
    @Operation(
            summary = "사용자 관심사별 거래 내역 리포트 가져오기",
            description = "사용자의 관심사별 거래 내역 리포트를 가져옵니다.",
            parameters = {
                @Parameter(name = "interestId", description = "관심사 ID", required = true),
                @Parameter(name = "userId", description = "사용자 ID", required = true),
                @Parameter(name = "year", description = "년도", required = true),
                @Parameter(name = "month", description = "월", required = true)
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "사용자 관심사별 거래 내역 리포트 가져오기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                UserInterestReportsResponse
                                                                        .class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "사용자 관심사별 거래 내역 리포트 가져오기 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
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
