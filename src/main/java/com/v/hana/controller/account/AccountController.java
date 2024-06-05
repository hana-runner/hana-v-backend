package com.v.hana.controller.account;

import com.v.hana.auth.annotation.CurrentUser;
import com.v.hana.auth.util.user.SecurityUtil;
import com.v.hana.command.account.*;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.*;
import com.v.hana.entity.user.User;
import com.v.hana.usecase.account.AccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@TypeInfo(name = "AccountController", description = "계좌 컨트롤러")
@RestController
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountUseCase accountUseCase;
    private final SecurityUtil securityUtil;

    // TODO : 회원 검증 로직 추가
    @MethodInfo(name = "getAccounts", description = "등록된 계좌 목록을 조회합니다.")
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
    @GetMapping("/accounts/{accountId}/history")
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
    @GetMapping("/accounts/categories")
    @CurrentUser
    public ResponseEntity<AccountExpenseResponse> getExpensePerCategories(@RequestParam(name = "start", required = false)
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                     LocalDate start, @RequestParam(name = "end", required = false)
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
        AccountExpenseResponse expensePerCategories = accountUseCase.getExpensePerCategories(GetExpenseCommand.builder()
                .userId(currentUser.getId())
                .start(start)
                .end(end)
                .build());

        return ResponseEntity.ok(expensePerCategories);
    }

}
