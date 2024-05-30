package com.v.hana.controller.transaction;

import com.v.hana.command.transaction.ReadTransactionHistoryByIdCommend;
import com.v.hana.command.transaction.ReadTransactionHistoryDetailsByIdCommend;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.transaction.TransactionHistoryGetRequest;
import com.v.hana.dto.transaction.TransactionHistoryGetResponse;
import com.v.hana.entity.interest.Interest;
import com.v.hana.entity.transaction.TransactionHistory;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import com.v.hana.usecase.transaction.TransactionHistoryDetailUseCase;
import com.v.hana.usecase.transaction.TransactionHistoryUseCase;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TypeInfo(name = "TransactionHistoryController", description = "거래내역 컨트롤러 클래스")
@RestController
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class TransactionHistoryController {
    private final TransactionHistoryUseCase transactionHistoryUseCase;
    private final TransactionHistoryDetailUseCase transactionHistoryDetailUseCase;

    @MethodInfo(name = "transactionHistoryGet", description = "거래내역 목록을 조회합니다.")
    @GetMapping("/transaction-histories/{transactionHistoryId}")
    public ResponseEntity<TransactionHistoryGetResponse> transactionHistoriesGet(
            @PathVariable Long transactionHistoryId,
            @Valid TransactionHistoryGetRequest transactionHistoryGetRequest) {
        // TODO: 회원 검증 로직 추가

        CompletableFuture<TransactionHistory> transactionHistoryFuture =
                CompletableFuture.supplyAsync(
                        () ->
                                transactionHistoryUseCase.readTransactionHistoryById(
                                        ReadTransactionHistoryByIdCommend.builder()
                                                .id(transactionHistoryId)
                                                .build()));
        CompletableFuture<ArrayList<TransactionHistoryDetail>> transactionHistoryDetailsFuture =
                CompletableFuture.supplyAsync(
                        () ->
                                transactionHistoryDetailUseCase.readTransactionHistoryDetailsById(
                                        ReadTransactionHistoryDetailsByIdCommend.builder()
                                                .id(transactionHistoryId)
                                                .build()));

        CompletableFuture.allOf(transactionHistoryFuture, transactionHistoryDetailsFuture).join();

        TransactionHistory transactionHistory = transactionHistoryFuture.join();
        ArrayList<TransactionHistoryDetail> transactionHistoryDetails =
                transactionHistoryDetailsFuture.join();

        return ResponseEntity.ok(
                TransactionHistoryGetResponse.builder()
                        .id(transactionHistory.getId())
                        .accountId(transactionHistory.getAccount().getId())
                        .categoryTitle(transactionHistory.getCategory().getTitle())
                        .categoryColor(transactionHistory.getCategory().getColor())
                        .approvalNumber(transactionHistory.getApprovalNumber())
                        .type(transactionHistory.getType())
                        .description(transactionHistory.getDescription())
                        .action(transactionHistory.getAction())
                        .amount(transactionHistory.getAmount())
                        .balance(transactionHistory.getBalance())
                        .createdAt(transactionHistory.getCreatedAt())
                        .interests(
                                transactionHistoryDetails.stream()
                                        .map(TransactionHistoryDetail::getInterest)
                                        .map(
                                                interest ->
                                                        Interest.builder()
                                                                .id(interest.getId())
                                                                .title(interest.getTitle())
                                                                .description(
                                                                        interest.getDescription())
                                                                .baseImageUrl(
                                                                        interest.getBaseImageUrl())
                                                                .color(interest.getColor())
                                                                .build())
                                        .collect(Collectors.toCollection(ArrayList::new)))
                        .build());
    }
}
