package com.v.hana.controller.transaction;

import com.v.hana.auth.annotation.CurrentUser;
import com.v.hana.auth.util.user.SecurityUtil;
import com.v.hana.command.category.FindCategoryByIdCommand;
import com.v.hana.command.transaction.ReadTransactionHistoryByIdCommend;
import com.v.hana.command.transaction.ReadTransactionHistoryDetailsByIdCommend;
import com.v.hana.command.transaction.UpdateTransactionHistoryCommand;
import com.v.hana.command.transaction.UpdateTransactionHistoryDetailCommend;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.exception.BaseExceptionResponse;
import com.v.hana.dto.account.ExpensePerInterest;
import com.v.hana.dto.account.ExpenseResponse;
import com.v.hana.dto.interest.InterestDto;
import com.v.hana.dto.transaction.*;
import com.v.hana.entity.transaction.TransactionHistory;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import com.v.hana.entity.user.User;
import com.v.hana.usecase.category.CategoryUseCase;
import com.v.hana.usecase.transaction.TransactionHistoryDetailUseCase;
import com.v.hana.usecase.transaction.TransactionHistoryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "TransactionHistoryController", description = "거래내역 컨트롤러 클래스")
@RestController
@Tag(name = "TransactionHistory", description = "거래 내역")
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class TransactionHistoryController {
    private final TransactionHistoryUseCase transactionHistoryUseCase;
    private final TransactionHistoryDetailUseCase transactionHistoryDetailUseCase;
    private final CategoryUseCase categoryUseCase;
    private final SecurityUtil securityUtil;

    @MethodInfo(name = "transactionHistoryGet", description = "거래내역 목록을 조회합니다.")
    @Operation(
            summary = "거래내역 목록 조회",
            description = "거래내역 목록을 조회합니다.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "거래내역 목록 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                TransactionHistoryGetResponse
                                                                        .class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "거래내역 목록 조회 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    @GetMapping("/transaction-histories/{transactionHistoryId}")
    @CurrentUser
    public ResponseEntity<TransactionHistoryGetResponse> transactionHistoriesGet(
            @PathVariable Long transactionHistoryId) {
        User user = securityUtil.getCurrentUser();

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
                        .categoryId(transactionHistory.getCategory().getId())
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
                                                                .id(
                                                                        transactionHistoryDetail
                                                                                .getId())
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
    @Operation(
            summary = "거래내역 수정",
            description = "거래내역의 카테고리를 수정합니다.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "거래내역 수정 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                TransactionHistoryPutResponse
                                                                        .class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "거래내역 수정 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    @PutMapping("/transaction-histories/{transactionHistoryId}")
    @CurrentUser
    public ResponseEntity<TransactionHistoryPutResponse> transactionHistoriesPut(
            @PathVariable Long transactionHistoryId,
            @Valid @RequestBody TransactionHistoryPutRequest transactionHistoryPutRequest) {
        User user = securityUtil.getCurrentUser();

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
                        .categoryId(transactionHistory.getCategory().getId())
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
                                                                .id(
                                                                        transactionHistoryDetail
                                                                                .getId())
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
    @Operation(
            summary = "거래내역 상세 수정",
            description = "거래내역 상세를 수정합니다.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "거래내역 상세 수정 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                TransactionHistoryDetailsPostResponse
                                                                        .class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "거래내역 상세 수정 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    @PostMapping("/transaction-history-details/transaction-history/{transactionHistoryId}")
    @CurrentUser
    public ResponseEntity<TransactionHistoryDetailsPostResponse>
            transactionHistoryDetailsPostResponse(
                    @PathVariable Long transactionHistoryId,
                    @Valid @RequestBody
                            TransactionHistoryDetailsPostRequest
                                    transactionHistoryDetailsPostRequest) {
        User user = securityUtil.getCurrentUser();

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
                                                .interests(
                                                        transactionHistoryDetailsPostRequest
                                                                .getInterests())
                                                .build()));

        CompletableFuture.allOf(transactionHistoryFuture, transactionHistoryDetailsFuture).join();

        TransactionHistory transactionHistory = transactionHistoryFuture.join();
        ArrayList<TransactionHistoryDetail> transactionHistoryDetails =
                transactionHistoryDetailsFuture.join();

        return ResponseEntity.ok(
                TransactionHistoryDetailsPostResponse.builder()
                        .id(transactionHistory.getId())
                        .accountId(transactionHistory.getAccount().getId())
                        .categoryId(transactionHistory.getCategory().getId())
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
                                                                .id(
                                                                        transactionHistoryDetail
                                                                                .getId())
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

    @MethodInfo(name = "getExpensePerInterests", description = "카테고리 지출에 대한 관심사별 지출 합계를 조회합니다.")
    @GetMapping("/transaction-history-details/expenses")
    @CurrentUser
    public ResponseEntity<ExpenseResponse> getExpensePerInterests(
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
            end = LocalDate.now().plusDays(1);
            //            end = LocalDate.of(2024, 5, 5);
        }
        User currentUser = securityUtil.getCurrentUser();
        ArrayList<ExpensePerInterest> expensePerInterests =
                transactionHistoryDetailUseCase.getExpensePerInterests(
                        currentUser.getId(), start, end);
        return ResponseEntity.ok(ExpenseResponse.builder().data(expensePerInterests).build());
    }
}
