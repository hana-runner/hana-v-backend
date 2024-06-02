package com.v.hana.service.account;

import com.v.hana.command.account.GetAccountsCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.AccountDto;
import com.v.hana.dto.account.AccountGetResponse;
import com.v.hana.entity.account.Account;
import com.v.hana.entity.user.User;
import com.v.hana.repository.account.AccountRepository;
import com.v.hana.usecase.account.AccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@TypeInfo(name = "AccountService", description = "계좌 서비스 클래스")
@Service
@RequiredArgsConstructor
public class AccountService implements AccountUseCase {
    private final AccountRepository accountRepository;

    @MethodInfo(name = "getAccounts", description = "등록된 계좌 목록을 조회합니다.")
    @Override
    public AccountGetResponse getAccounts(GetAccountsCommand command) {

        return AccountGetResponse.builder().data(accountRepository.findByUserId(command.getUserId()).stream()
                        .map(
                                account -> {
                                    Account accountFound = accountRepository.findById(account.getId())
                                            .orElseThrow(
                                                    () -> new RuntimeException("Account not found")
                                            );
                                    return AccountDto.builder()
                                            .id(accountFound.getId())
                                            .accountName(accountFound.getAccountName())
                                            .accountNumber(accountFound.getAccountNumber())
                                            .accountType(accountFound.getAccountType())
                                            .balance(accountFound.getBalance())
                                            .build();
                                })
                        .collect(Collectors.toCollection(ArrayList::new)))
                .build();
    }

}
