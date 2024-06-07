package com.v.hana.controller.account;

import com.v.hana.auth.annotation.CurrentUser;
import com.v.hana.auth.util.user.SecurityUtil;
import com.v.hana.command.account.*;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.*;
import com.v.hana.entity.user.User;
import com.v.hana.usecase.account.AccountUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "AccountController", description = "계좌 컨트롤러")
@RestController
@Tag(name = "Account", description = "계좌")
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountUseCase accountUseCase;
    private final SecurityUtil securityUtil;

    @MethodInfo(name = "getAccounts", description = "등록된 계좌 목록을 조회합니다.")
    @Operation(
            summary = "등록된 계좌 목록 조회",
            description = "등록된 계좌 목록을 조회합니다.",
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "등록된 계좌 목록 조회 성공",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountGetResponse.class))
                        }),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "등록된 계좌 목록 조회 실패",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountGetResponse.class))
                        }),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountGetResponse.class))
                        })
            })
    @GetMapping("/accounts")
    @CurrentUser
    public ResponseEntity<AccountGetResponse> getAccounts() {
        User currentUser = securityUtil.getCurrentUser();
        AccountGetResponse accounts =
                accountUseCase.getAccounts(
                        GetAccountsCommand.builder().userId(currentUser.getId()).build());

        return ResponseEntity.ok(accounts);
    }

    @MethodInfo(name = "TransactionsGet", description = "계좌의 거래 내역을 조회합니다.")
    @Operation(
            summary = "계좌 거래 내역 조회",
            description = "계좌의 거래 내역을 조회합니다.",
            parameters = {
                @Parameter(name = "accountId", description = "계좌 ID", required = true),
                @Parameter(name = "option", description = "옵션", required = false),
                @Parameter(name = "sort", description = "정렬", required = false),
                @Parameter(name = "start", description = "시작일", required = false),
                @Parameter(name = "end", description = "종료일", required = false)
            },
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "계좌 거래 내역 조회 성공",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation =
                                                            AccountTransactionGetResponse.class))
                        }),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "계좌 거래 내역 조회 실패",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation =
                                                            AccountTransactionGetResponse.class))
                        }),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation =
                                                            AccountTransactionGetResponse.class))
                        })
            })
    @GetMapping("/accounts/{accountId}/history")
    @CurrentUser
    public ResponseEntity<AccountTransactionGetResponse> TransactionsGet(
            @PathVariable(name = "accountId") Long accountId,
            @RequestParam(name = "option", required = false, defaultValue = "0") Integer option,
            @RequestParam(name = "sort", required = false, defaultValue = "true") Boolean sort,
            @RequestParam(name = "start", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate start,
            @RequestParam(name = "end", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate end) {
        User user = securityUtil.getCurrentUser();

        if (start == null) {
            start = LocalDate.now().minusMonths(1);
        }
        if (end == null) {
            end = LocalDate.now();
        }

        return ResponseEntity.ok(
                accountUseCase.readTransactionHistories(
                        ReadTransactionsCommand.builder()
                                .accountId(accountId)
                                .option(option)
                                .sort(sort)
                                .start(start.atStartOfDay())
                                .end(end.atTime(LocalTime.MAX))
                                .build()));
    }

    @MethodInfo(name = "checkAccountNumber", description = "등록 요청한 계좌번호가 유효한지 확인합니다.")
    @Operation(
            summary = "계좌번호 확인",
            description = "등록 요청한 계좌번호가 유효한지 확인합니다.",
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "계좌번호 확인 성공",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountCheckResponse.class))
                        }),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "계좌번호 확인 실패",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountCheckResponse.class))
                        }),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountCheckResponse.class))
                        })
            })
    @PostMapping("/accounts/check/account-info")
    public ResponseEntity<AccountCheckResponse> checkAccountNumber(
            @RequestBody AccountCheckRequest request) {
        AccountCheckResponse checkedAccountNumber =
                accountUseCase.checkAccountNumber(
                        CheckAccountNumberCommand.builder()
                                .accountNumber(request.getAccountNumber())
                                .build());

        return ResponseEntity.ok(checkedAccountNumber);
    }

    @MethodInfo(name = "registerAccount", description = "계좌정보를 등록합니다.")
    @Operation(
            summary = "계좌 등록",
            description = "계좌정보를 등록합니다.",
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "계좌 등록 성공",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountRegisterResponse.class))
                        }),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "계좌 등록 실패",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountRegisterResponse.class))
                        }),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountRegisterResponse.class))
                        })
            })
    @PostMapping("/accounts")
    @CurrentUser
    public ResponseEntity<AccountRegisterResponse> registerAccount(
            @RequestBody AccountRegisterRequest request) {
        // TODO : 회원 검증 로직 추가
        User currentUser = securityUtil.getCurrentUser();
        AccountRegisterResponse registeredAccount =
                accountUseCase.registerAccount(
                        RegisterAccountCommand.builder()
                                .user(currentUser)
                                .bankName(request.getBankName())
                                .accountNumber(request.getAccountNumber())
                                .accountType(request.getAccountType())
                                .accountName(request.getAccountName())
                                .balance(request.getBalance())
                                .build());
        return ResponseEntity.ok(registeredAccount);
    }

    @MethodInfo(name = "getExpensePerCategories", description = "카테고리별 지출 합계를 조회합니다.")
    @Operation(
            summary = "카테고리별 지출 합계 조회",
            description = "카테고리별 지출 합계를 조회합니다.",
            parameters = {
                @Parameter(name = "start", description = "시작일", required = false),
                @Parameter(name = "end", description = "종료일", required = false)
            },
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "카테고리별 지출 합계 조회 성공",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountExpenseResponse.class))
                        }),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "카테고리별 지출 합계 조회 실패",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountExpenseResponse.class))
                        }),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                            @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = AccountExpenseResponse.class))
                        })
            })
    @GetMapping("/accounts/categories")
    @CurrentUser
    public ResponseEntity<AccountExpenseResponse> getExpensePerCategories(
            @RequestParam(name = "start", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate start,
            @RequestParam(name = "end", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate end) {
        if (start == null) {
            start = LocalDate.now().minusMonths(1);
            //            start = LocalDate.of(2024, 4, 5);
        }

        if (end == null) {
            end = LocalDate.now();
            //            end = LocalDate.of(2024, 5, 5);
        }
        User currentUser = securityUtil.getCurrentUser();
        AccountExpenseResponse expensePerCategories =
                accountUseCase.getExpensePerCategories(
                        GetExpenseCommand.builder()
                                .userId(currentUser.getId())
                                .start(start)
                                .end(end)
                                .build());

        return ResponseEntity.ok(expensePerCategories);
    }
}
