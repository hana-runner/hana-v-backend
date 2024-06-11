package com.v.hana.event.account;

import com.v.hana.command.transaction.ReadTransactionsCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.AccountTransactionGetResponse;
import com.v.hana.dto.interest.InterestDto;
import com.v.hana.dto.transaction.TransactionHistoryDetailDto;
import com.v.hana.dto.transaction.TransactionHistoryDto;
import com.v.hana.entity.transaction.TransactionHistory;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import com.v.hana.exception.transaction.ReadTransactionFailException;
import com.v.hana.usecase.transaction.TransactionHistoryDetailUseCase;
import com.v.hana.usecase.transaction.TransactionHistoryUseCase;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@TypeInfo(name = "ReadAccountTransactionEventListener", description = "계좌 거래 내역 조회 이벤트 핸들러")
@Component
public class ReadAccountTransactionEventListener {
    private final TransactionHistoryUseCase transactionHistoryUseCase;
    private final TransactionHistoryDetailUseCase transactionHistoryDetailUseCase;

    @MethodInfo(name = "handle", description = "계좌 거래 내역 조회 이벤트를 처리합니다.")
    @TransactionalEventListener(fallbackExecution = true)
    public AccountTransactionGetResponse handle(
            ReadAccountTransactionEvent readAccountTransactionEvent) {
        CompletableFuture<ArrayList<TransactionHistory>> transactionHistoryFuture =
                CompletableFuture.supplyAsync(
                        () ->
                                transactionHistoryUseCase.readTransactionHistory(
                                        ReadTransactionsCommand.builder()
                                                .account(readAccountTransactionEvent.getAccount())
                                                .option(readAccountTransactionEvent.getOption())
                                                .sort(readAccountTransactionEvent.getSort())
                                                .start(readAccountTransactionEvent.getStart())
                                                .end(readAccountTransactionEvent.getEnd())
                                                .build()));

        CompletableFuture<ArrayList<TransactionHistoryDetail>> transactionHistoryDetailsFuture =
                CompletableFuture.supplyAsync(
                        () ->
                                transactionHistoryDetailUseCase.readTransactionHistoryDetails(
                                        ReadTransactionsCommand.builder()
                                                .account(readAccountTransactionEvent.getAccount())
                                                .option(readAccountTransactionEvent.getOption())
                                                .sort(readAccountTransactionEvent.getSort())
                                                .start(readAccountTransactionEvent.getStart())
                                                .end(readAccountTransactionEvent.getEnd())
                                                .build()));

        CompletableFuture.allOf(transactionHistoryFuture, transactionHistoryDetailsFuture).join();

        ArrayList<TransactionHistory> transactionHistories = transactionHistoryFuture.join();
        ArrayList<TransactionHistoryDetail> transactionHistoryDetails =
                transactionHistoryDetailsFuture.join();

        if (readAccountTransactionEvent.getOption() == 0) {
            return AccountTransactionGetResponse.builder()
                    .data(
                            transactionHistories.stream()
                                    .map(
                                            transactionHistory ->
                                                    TransactionHistoryDto.builder()
                                                            .id(transactionHistory.getId())
                                                            .accountId(
                                                                    transactionHistory
                                                                            .getAccount()
                                                                            .getId())
                                                            .categoryTitle(
                                                                    transactionHistory
                                                                            .getCategory()
                                                                            .getTitle())
                                                            .categoryColor(
                                                                    transactionHistory
                                                                            .getCategory()
                                                                            .getColor())
                                                            .approvalNumber(
                                                                    transactionHistory
                                                                            .getApprovalNumber())
                                                            .type(transactionHistory.getType())
                                                            .description(
                                                                    transactionHistory
                                                                            .getDescription())
                                                            .action(transactionHistory.getAction())
                                                            .amount(transactionHistory.getAmount())
                                                            .balance(
                                                                    transactionHistory.getBalance())
                                                            .createdAt(
                                                                    transactionHistory
                                                                            .getCreatedAt())
                                                            .transactionHistoryDetails(
                                                                    transactionHistoryDetails
                                                                            .stream()
                                                                            .filter(
                                                                                    transactionHistoryDetail ->
                                                                                            transactionHistoryDetail
                                                                                                    .getTransactionHistory()
                                                                                                    .getId()
                                                                                                    .equals(
                                                                                                            transactionHistory
                                                                                                                    .getId()))
                                                                            .map(
                                                                                    transactionHistoryDetail ->
                                                                                            TransactionHistoryDetailDto
                                                                                                    .builder()
                                                                                                    .id(
                                                                                                            transactionHistoryDetail
                                                                                                                    .getId())
                                                                                                    .interest(
                                                                                                            InterestDto
                                                                                                                    .builder()
                                                                                                                    .interestId(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getId())
                                                                                                                    .title(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getTitle())
                                                                                                                    .description(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getDescription())
                                                                                                                    .color(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getColor())
                                                                                                                    .build())
                                                                                                    .description(
                                                                                                            transactionHistoryDetail
                                                                                                                    .getDescription())
                                                                                                    .amount(
                                                                                                            transactionHistoryDetail
                                                                                                                    .getAmount())
                                                                                                    .build())
                                                                            .collect(
                                                                                    Collectors
                                                                                            .toCollection(
                                                                                                    ArrayList
                                                                                                            ::new)))
                                                            .build())
                                    .collect(Collectors.toCollection(ArrayList::new)))
                    .build();
        } else if (readAccountTransactionEvent.getOption() == 1) {
            return AccountTransactionGetResponse.builder()
                    .data(
                            transactionHistories.stream()
                                    .filter(
                                            transactionHistory ->
                                                    transactionHistory.getAction().equals("입금"))
                                    .map(
                                            transactionHistory ->
                                                    TransactionHistoryDto.builder()
                                                            .id(transactionHistory.getId())
                                                            .accountId(
                                                                    transactionHistory
                                                                            .getAccount()
                                                                            .getId())
                                                            .categoryTitle(
                                                                    transactionHistory
                                                                            .getCategory()
                                                                            .getTitle())
                                                            .categoryColor(
                                                                    transactionHistory
                                                                            .getCategory()
                                                                            .getColor())
                                                            .approvalNumber(
                                                                    transactionHistory
                                                                            .getApprovalNumber())
                                                            .type(transactionHistory.getType())
                                                            .description(
                                                                    transactionHistory
                                                                            .getDescription())
                                                            .action(transactionHistory.getAction())
                                                            .amount(transactionHistory.getAmount())
                                                            .balance(
                                                                    transactionHistory.getBalance())
                                                            .createdAt(
                                                                    transactionHistory
                                                                            .getCreatedAt())
                                                            .transactionHistoryDetails(
                                                                    transactionHistoryDetails
                                                                            .stream()
                                                                            .filter(
                                                                                    transactionHistoryDetail ->
                                                                                            transactionHistoryDetail
                                                                                                    .getTransactionHistory()
                                                                                                    .getId()
                                                                                                    .equals(
                                                                                                            transactionHistory
                                                                                                                    .getId()))
                                                                            .map(
                                                                                    transactionHistoryDetail ->
                                                                                            TransactionHistoryDetailDto
                                                                                                    .builder()
                                                                                                    .id(
                                                                                                            transactionHistoryDetail
                                                                                                                    .getId())
                                                                                                    .interest(
                                                                                                            InterestDto
                                                                                                                    .builder()
                                                                                                                    .interestId(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getId())
                                                                                                                    .title(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getTitle())
                                                                                                                    .description(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getDescription())
                                                                                                                    .color(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getColor())
                                                                                                                    .build())
                                                                                                    .description(
                                                                                                            transactionHistoryDetail
                                                                                                                    .getDescription())
                                                                                                    .amount(
                                                                                                            transactionHistoryDetail
                                                                                                                    .getAmount())
                                                                                                    .build())
                                                                            .collect(
                                                                                    Collectors
                                                                                            .toCollection(
                                                                                                    ArrayList
                                                                                                            ::new)))
                                                            .build())
                                    .collect(Collectors.toCollection(ArrayList::new)))
                    .build();
        } else if (readAccountTransactionEvent.getOption() == 2) {
            return AccountTransactionGetResponse.builder()
                    .data(
                            transactionHistories.stream()
                                    .filter(
                                            transactionHistory ->
                                                    transactionHistory.getAction().equals("출금"))
                                    .map(
                                            transactionHistory ->
                                                    TransactionHistoryDto.builder()
                                                            .id(transactionHistory.getId())
                                                            .accountId(
                                                                    transactionHistory
                                                                            .getAccount()
                                                                            .getId())
                                                            .categoryTitle(
                                                                    transactionHistory
                                                                            .getCategory()
                                                                            .getTitle())
                                                            .categoryColor(
                                                                    transactionHistory
                                                                            .getCategory()
                                                                            .getColor())
                                                            .approvalNumber(
                                                                    transactionHistory
                                                                            .getApprovalNumber())
                                                            .type(transactionHistory.getType())
                                                            .description(
                                                                    transactionHistory
                                                                            .getDescription())
                                                            .action(transactionHistory.getAction())
                                                            .amount(transactionHistory.getAmount())
                                                            .balance(
                                                                    transactionHistory.getBalance())
                                                            .createdAt(
                                                                    transactionHistory
                                                                            .getCreatedAt())
                                                            .transactionHistoryDetails(
                                                                    transactionHistoryDetails
                                                                            .stream()
                                                                            .filter(
                                                                                    transactionHistoryDetail ->
                                                                                            transactionHistoryDetail
                                                                                                    .getTransactionHistory()
                                                                                                    .getId()
                                                                                                    .equals(
                                                                                                            transactionHistory
                                                                                                                    .getId()))
                                                                            .map(
                                                                                    transactionHistoryDetail ->
                                                                                            TransactionHistoryDetailDto
                                                                                                    .builder()
                                                                                                    .id(
                                                                                                            transactionHistoryDetail
                                                                                                                    .getId())
                                                                                                    .interest(
                                                                                                            InterestDto
                                                                                                                    .builder()
                                                                                                                    .interestId(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getId())
                                                                                                                    .title(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getTitle())
                                                                                                                    .description(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getDescription())
                                                                                                                    .color(
                                                                                                                            transactionHistoryDetail
                                                                                                                                    .getInterest()
                                                                                                                                    .getColor())
                                                                                                                    .build())
                                                                                                    .description(
                                                                                                            transactionHistoryDetail
                                                                                                                    .getDescription())
                                                                                                    .amount(
                                                                                                            transactionHistoryDetail
                                                                                                                    .getAmount())
                                                                                                    .build())
                                                                            .collect(
                                                                                    Collectors
                                                                                            .toCollection(
                                                                                                    ArrayList
                                                                                                            ::new)))
                                                            .build())
                                    .collect(Collectors.toCollection(ArrayList::new)))
                    .build();
        } else {
            throw new ReadTransactionFailException();
        }
    }

    @Builder
    public ReadAccountTransactionEventListener(
            TransactionHistoryUseCase transactionHistoryUseCase,
            TransactionHistoryDetailUseCase transactionHistoryDetailUseCase) {
        this.transactionHistoryUseCase = transactionHistoryUseCase;
        this.transactionHistoryDetailUseCase = transactionHistoryDetailUseCase;
    }
}
