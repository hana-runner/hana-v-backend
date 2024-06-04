package com.v.hana.event.account;

import com.v.hana.command.transaction.ReadTransactionsCommand;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.AccountTransactionGetResponse;
import com.v.hana.dto.transaction.TransactionDto;
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
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@TypeInfo(name = "ReadAccountTransactionEventHandler", description = "계좌 거래 내역 조회 이벤트 핸들러")
@Component
public class ReadAccountTransactionEventHandler {
    private final TransactionHistoryUseCase transactionHistoryUseCase;
    private final TransactionHistoryDetailUseCase transactionHistoryDetailUseCase;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
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
                                    .filter(
                                            transactionHistory ->
                                                    transactionHistory
                                                            .getAccount()
                                                            .getId()
                                                            .equals(
                                                                    readAccountTransactionEvent
                                                                            .getAccount()
                                                                            .getId()))
                                    .filter(
                                            transactionHistory ->
                                                    transactionHistory
                                                                    .getCreatedAt()
                                                                    .isAfter(
                                                                            readAccountTransactionEvent
                                                                                    .getStart())
                                                            && transactionHistory
                                                                    .getCreatedAt()
                                                                    .isBefore(
                                                                            readAccountTransactionEvent
                                                                                    .getEnd()))
                                    .map(
                                            transactionHistory ->
                                                    TransactionDto.builder()
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
                                                            .interests(
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
                                                                                    TransactionHistoryDetail
                                                                                            ::getInterest)
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
                                                    transactionHistory
                                                            .getAccount()
                                                            .getId()
                                                            .equals(
                                                                    readAccountTransactionEvent
                                                                            .getAccount()
                                                                            .getId()))
                                    .filter(
                                            transactionHistory ->
                                                    transactionHistory.getType().equals("입금"))
                                    .filter(
                                            transactionHistory ->
                                                    transactionHistory
                                                                    .getCreatedAt()
                                                                    .isAfter(
                                                                            readAccountTransactionEvent
                                                                                    .getStart())
                                                            && transactionHistory
                                                                    .getCreatedAt()
                                                                    .isBefore(
                                                                            readAccountTransactionEvent
                                                                                    .getEnd()))
                                    .map(
                                            transactionHistory ->
                                                    TransactionDto.builder()
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
                                                            .interests(
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
                                                                                    TransactionHistoryDetail
                                                                                            ::getInterest)
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
                                                    transactionHistory
                                                            .getAccount()
                                                            .getId()
                                                            .equals(
                                                                    readAccountTransactionEvent
                                                                            .getAccount()
                                                                            .getId()))
                                    .filter(
                                            transactionHistory ->
                                                    transactionHistory.getType().equals("출금"))
                                    .filter(
                                            transactionHistory ->
                                                    transactionHistory
                                                                    .getCreatedAt()
                                                                    .isAfter(
                                                                            readAccountTransactionEvent
                                                                                    .getStart())
                                                            && transactionHistory
                                                                    .getCreatedAt()
                                                                    .isBefore(
                                                                            readAccountTransactionEvent
                                                                                    .getEnd()))
                                    .map(
                                            transactionHistory ->
                                                    TransactionDto.builder()
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
                                                            .interests(
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
                                                                                    TransactionHistoryDetail
                                                                                            ::getInterest)
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
    public ReadAccountTransactionEventHandler(
            TransactionHistoryUseCase transactionHistoryUseCase,
            TransactionHistoryDetailUseCase transactionHistoryDetailUseCase) {
        this.transactionHistoryUseCase = transactionHistoryUseCase;
        this.transactionHistoryDetailUseCase = transactionHistoryDetailUseCase;
    }
}
