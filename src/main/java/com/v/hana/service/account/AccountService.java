package com.v.hana.service.account;

import com.v.hana.command.account.*;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.*;
import com.v.hana.entity.account.Account;
import com.v.hana.event.account.ReadAccountTransactionEvent;
import com.v.hana.event.account.ReadAccountTransactionEventListener;
import com.v.hana.exception.account.AccountNotFoundException;
import com.v.hana.exception.transaction.ExpenseNotFoundException;
import com.v.hana.repository.account.AccountApiRepository;
import com.v.hana.repository.account.AccountRepository;
import com.v.hana.repository.transaction.TransactionHistoryRepository;
import com.v.hana.usecase.account.AccountUseCase;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@TypeInfo(name = "AccountService", description = "계좌 서비스 클래스")
@Service
@RequiredArgsConstructor
public class AccountService implements AccountUseCase {
    private final AccountRepository accountRepository;
    private final AccountApiRepository accountApiRepository;
    private final ReadAccountTransactionEventListener readAccountTransactionEventListener;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @MethodInfo(name = "getAccounts", description = "등록된 계좌 목록을 조회합니다.")
    @Override
    public AccountGetResponse getAccounts(GetAccountsCommand command) {

        return AccountGetResponse.builder()
                .data(
                        accountRepository.findByUserId(command.getUserId()).stream()
                                .map(
                                        account -> {
                                            Account accountFound =
                                                    accountRepository
                                                            .findById(account.getId())
                                                            .orElseThrow(
                                                                    AccountNotFoundException::new);
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

    @MethodInfo(name = "readTransactionHistories", description = "계좌의 거래 내역을 조회합니다.")
    @Override
    public AccountTransactionGetResponse readTransactionHistories(
            ReadTransactionsCommand readTransactionsCommand) {
        return readAccountTransactionEventListener.handle(
                ReadAccountTransactionEvent.builder()
                        .account(
                                accountRepository
                                        .findById(readTransactionsCommand.getAccountId())
                                        .orElseThrow(AccountNotFoundException::new))
                        .option(readTransactionsCommand.getOption())
                        .sort(readTransactionsCommand.getSort())
                        .start(readTransactionsCommand.getStart())
                        .end(readTransactionsCommand.getEnd())
                        .build());
    }

    @MethodInfo(name = "checkAccountNumber", description = "등록 요청한 계좌번호가 유효한지 확인합니다.")
    @Override
    public AccountCheckResponse checkAccountNumber(CheckAccountNumberCommand command) {

        return AccountCheckResponse.builder()
                .data(
                        accountApiRepository
                                .findByAccountNumber(command.getAccountNumber())
                                .orElseThrow(AccountNotFoundException::new))
                .build();
    }

    @MethodInfo(name = "registerAccount", description = "계좌정보를 등록합니다.")
    @Override
    public AccountRegisterResponse registerAccount(RegisterAccountCommand command) {
        Account savedAccount =
                accountRepository.save(
                        Account.builder()
                                .user(command.getUser())
                                .bankName(command.getBankName())
                                .accountNumber(command.getAccountNumber())
                                .accountName(command.getAccountName())
                                .accountType(command.getAccountType())
                                .balance(command.getBalance())
                                .build());
        return AccountRegisterResponse.builder().build();
    }

    @MethodInfo(name = "getExpensePerCategories", description = "카테고리별 지출 합계를 조회합니다.")
    @Override
    public AccountExpenseResponse getExpensePerCategories(GetExpenseCommand command) {
        ArrayList<ExpensePerCategory> expensePerCategories =
                transactionHistoryRepository.getExpensePerCategories(
                        command.getUserId(), command.getStart(), command.getEnd());
        if (expensePerCategories.isEmpty()) {
            throw new ExpenseNotFoundException();
        }
        return AccountExpenseResponse.builder().data(expensePerCategories).build();
    }

    @MethodInfo(name = "deleteAccountInfo", description = "등록된 계좌 정보를 삭제합니다.")
    @Override
    public AccountDeleteResponse deleteAccountInfo(Long accountId) {
        accountRepository.findById(accountId).ifPresent(
                account -> {
                    accountRepository.delete(account);
                }
        );
        return AccountDeleteResponse.builder().build();
    }
}
