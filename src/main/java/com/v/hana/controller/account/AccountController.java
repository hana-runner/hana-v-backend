package com.v.hana.controller.account;

import com.v.hana.command.account.GetAccountsCommand;
import com.v.hana.command.account.ReadTransactionsCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.AccountGetResponse;
import com.v.hana.dto.account.AccountTransactionGetResponse;
import com.v.hana.repository.user.UserRepository;
import com.v.hana.usecase.account.AccountUseCase;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "AccountController", description = "계좌 컨트롤러")
@RestController
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountUseCase accountUseCase;
    private final UserRepository userRepository;

    @MethodInfo(name = "getAccounts", description = "등록된 계좌 목록을 조회합니다.")
    @GetMapping("/accounts")
    public ResponseEntity<AccountGetResponse> getAccount() {
        // TODO : 회원 검증 로직 추가
        AccountGetResponse accounts =
                accountUseCase.getAccounts(GetAccountsCommand.builder().userId(1L).build());

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
}
