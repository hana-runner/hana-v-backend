package com.v.hana.controller.transaction;

import com.v.hana.command.category.FindCategoryByIdCommand;
import com.v.hana.command.transaction.ReadTransactionHistoryByIdCommend;
import com.v.hana.command.transaction.ReadTransactionHistoryDetailsByIdCommend;
import com.v.hana.command.transaction.UpdateTransactionHistoryCommand;
import com.v.hana.command.transaction.UpdateTransactionHistoryDetailCommend;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.interest.InterestDto;
import com.v.hana.dto.transaction.*;
import com.v.hana.entity.transaction.TransactionHistory;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import com.v.hana.usecase.category.CategoryUseCase;
import com.v.hana.usecase.transaction.TransactionHistoryDetailUseCase;
import com.v.hana.usecase.transaction.TransactionHistoryUseCase;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "TransactionHistoryController", description = "거래내역 컨트롤러 클래스")
@RestController
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class TransactionHistoryController {
    private final TransactionHistoryUseCase transactionHistoryUseCase;
    private final TransactionHistoryDetailUseCase transactionHistoryDetailUseCase;
    private final CategoryUseCase categoryUseCase;

    @MethodInfo(name = "transactionHistoryGet", description = "거래내역 목록을 조회합니다.")
    @GetMapping("/transaction-histories/{transactionHistoryId}")
    public ResponseEntity<TransactionHistoryGetResponse> transactionHistoriesGet(
            @PathVariable Long transactionHistoryId) {
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
                        .transactionHistoryDetails(
                                transactionHistoryDetails.stream()
                                        .filter(
                                                transactionHistoryDetail ->
                                                        transactionHistoryDetail
                                                                .getTransactionHistory()
                                                                .getId()
                                                                .equals(transactionHistory.getId()))
                                        .map(
                                                transactionHistoryDetail ->
                                                        TransactionHistoryDetailDto.builder()
                                                                .interest(
                                                                        InterestDto.builder()
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
                                        .collect(Collectors.toCollection(ArrayList::new)))
                        .build());
    }

    @MethodInfo(name = "transactionHistoryPut", description = "거래내역의 카테고리를 수정합니다.")
    @PutMapping("/transaction-histories/{transactionHistoryId}")
    public ResponseEntity<TransactionHistoryPutResponse> transactionHistoriesPut(
            @PathVariable Long transactionHistoryId,
            @Valid @RequestBody TransactionHistoryPutRequest transactionHistoryPutRequest) {
        // TODO: 회원 검증 로직 추가;

        CompletableFuture<TransactionHistory> transactionHistoryFuture =
                CompletableFuture.supplyAsync(
                        () ->
                                transactionHistoryUseCase.updateTransactionHistory(
                                        UpdateTransactionHistoryCommand.builder()
                                                .id(transactionHistoryId)
                                                .category(
                                                        categoryUseCase.readCategoryById(
                                                                FindCategoryByIdCommand.builder()
                                                                        .id(
                                                                                transactionHistoryPutRequest
                                                                                        .getCategoryId())
                                                                        .build()))
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
                TransactionHistoryPutResponse.builder()
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
                        .transactionHistoryDetails(
                                transactionHistoryDetails.stream()
                                        .filter(
                                                transactionHistoryDetail ->
                                                        transactionHistoryDetail
                                                                .getTransactionHistory()
                                                                .getId()
                                                                .equals(transactionHistory.getId()))
                                        .map(
                                                transactionHistoryDetail ->
                                                        TransactionHistoryDetailDto.builder()
                                                                .interest(
                                                                        InterestDto.builder()
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
                                        .collect(Collectors.toCollection(ArrayList::new)))
                        .build());
    }

    @MethodInfo(name = "transactionHistoryDetailsPost", description = "거래내역 상세를 수정합니다.")
    @PostMapping("/transaction-history-details/transaction-history/{transactionHistoryId}")
    public ResponseEntity<TransactionHistoryDetailsPostResponse>
            transactionHistoryDetailsPostResponse(
                    @PathVariable Long transactionHistoryId,
                    @Valid @RequestBody
                            TransactionHistoryDetailsPostRequest
                                    transactionHistoryDetailsPostRequest) {
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
                                transactionHistoryDetailUseCase.updateTransactionHistoryDetails(
                                        UpdateTransactionHistoryDetailCommend.builder()
                                                .id(transactionHistoryId)
                                                .interests(transactionHistoryDetailsPostRequest.getInterests())
                                                .build()));

        CompletableFuture.allOf(transactionHistoryFuture, transactionHistoryDetailsFuture).join();

        TransactionHistory transactionHistory = transactionHistoryFuture.join();
        ArrayList<TransactionHistoryDetail> transactionHistoryDetails =
                transactionHistoryDetailsFuture.join();

        return ResponseEntity.ok(
                TransactionHistoryDetailsPostResponse.builder()
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
                        .transactionHistoryDetails(
                                transactionHistoryDetails.stream()
                                        .filter(
                                                transactionHistoryDetail ->
                                                        transactionHistoryDetail
                                                                .getTransactionHistory()
                                                                .getId()
                                                                .equals(transactionHistory.getId()))
                                        .map(
                                                transactionHistoryDetail ->
                                                        TransactionHistoryDetailDto.builder()
                                                                .interest(
                                                                        InterestDto.builder()
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
                                        .collect(Collectors.toCollection(ArrayList::new)))
                        .build());
    }
}
