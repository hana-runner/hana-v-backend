package com.v.hana.controller.account;

import com.v.hana.command.account.GetAccountsCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.AccountGetResponse;
import com.v.hana.usecase.account.AccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TypeInfo(name = "AccountController", description = "계좌 컨트롤러")
@RestController
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountUseCase accountUseCase;

    @MethodInfo(name = "getAccounts", description = "등록된 계좌 목록을 조회합니다.")
    @GetMapping("/accounts")
    public ResponseEntity<AccountGetResponse> getAccount() {
        // TODO : 회원 검증 로직 추가
        AccountGetResponse accounts = accountUseCase.getAccounts(GetAccountsCommand.builder().userId(1L).build());

        return ResponseEntity.ok(accounts);
    }

}
