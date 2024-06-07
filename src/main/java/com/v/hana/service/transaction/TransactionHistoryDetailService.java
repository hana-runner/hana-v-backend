package com.v.hana.service.transaction;

import com.v.hana.command.transaction.ReadTransactionHistoryDetailsByIdCommend;
import com.v.hana.command.transaction.ReadTransactionsCommand;
import com.v.hana.command.transaction.UpdateTransactionHistoryDetailCommend;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.ExpensePerInterest;
import com.v.hana.dto.transaction.InterestDto;
import com.v.hana.entity.transaction.TransactionHistory;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import com.v.hana.exception.transaction.TransactionIdNotFoundException;
import com.v.hana.repository.interest.InterestRepository;
import com.v.hana.repository.transaction.TransactionHistoryDetailRepository;
import com.v.hana.repository.transaction.TransactionHistoryRepository;
import com.v.hana.usecase.transaction.TransactionHistoryDetailUseCase;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@TypeInfo(name = "TransactionHistoryDetailService", description = "거래내역 상세 서비스 클래스")
@Service
public class TransactionHistoryDetailService implements TransactionHistoryDetailUseCase {
    private final TransactionHistoryDetailRepository transactionHistoryDetailRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final InterestRepository interestRepository;

    @MethodInfo(
            name = "readTransactionHistoryDetailsById",
            description = "거래내역 상세 ID로 거래내역 상세 목록을 조회합니다.")
    @Override
    public ArrayList<TransactionHistoryDetail> readTransactionHistoryDetailsById(
            ReadTransactionHistoryDetailsByIdCommend command) {
        ArrayList<TransactionHistoryDetail> transactionHistoryDetails =
                transactionHistoryDetailRepository.findAllByTransactionHistoryId(command.getId());

        if (transactionHistoryDetails.isEmpty()) {
            throw new TransactionIdNotFoundException();
        }

        return transactionHistoryDetails;
    }

    @MethodInfo(name = "readTransactionHistoryDetails", description = "거래내역 상세 목록을 조회합니다.")
    @Override
    public ArrayList<TransactionHistoryDetail> readTransactionHistoryDetails(
            ReadTransactionsCommand readTransactionsCommand) {
        return transactionHistoryDetailRepository.findAll();
    }

    @MethodInfo(name = "updateTransactionHistoryDetails", description = "거래내역 상세 목록을 수정합니다.")
    @Transactional
    @Override
    public ArrayList<TransactionHistoryDetail> updateTransactionHistoryDetails(
            UpdateTransactionHistoryDetailCommend updateTransactionHistoryDetailCommend) {
        transactionHistoryDetailRepository.deleteAllByTransactionHistoryId(
                updateTransactionHistoryDetailCommend.getId());

        TransactionHistory transactionHistory =
                transactionHistoryRepository
                        .findById(updateTransactionHistoryDetailCommend.getId())
                        .get();

        for (InterestDto interestDto : updateTransactionHistoryDetailCommend.getInterests()) {
            transactionHistoryDetailRepository.insertTransactionHistoryDetail(
                    transactionHistory.getId(),
                    interestDto.getDescription(),
                    interestDto.getAmount(),
                    interestDto.getId(),
                    transactionHistory.getUser().getId(),
                    LocalDateTime.now().toString(),
                    LocalDateTime.now().toString());
        }

        return transactionHistoryDetailRepository.findAllByTransactionHistoryId(
                updateTransactionHistoryDetailCommend.getId());
    }

    @MethodInfo(
            name = "sumAmountByUserIdAndInterestId",
            description = "유저 ID와 관심사 ID로 거래내역 상세의 금액을 합산합니다.")
    @Override
    public Long sumAmountByUserIdAndInterestId(
            Long userId, Long interestId, LocalDate start, LocalDate end) {
        return transactionHistoryDetailRepository.sumAmountByUserIdAndInterestId(
                userId, interestId, start, end);
    }

    @MethodInfo(name = "sumAmountByInterestId", description = "관심사 ID로 거래내역 상세의 금액을 합산합니다.")
    @Override
    public Long sumAmountByInterestId(Long interestId, LocalDate start, LocalDate end) {
        return transactionHistoryDetailRepository.sumAmountByInterestId(interestId, start, end);
    }

    @MethodInfo(name = "getExpensePerInterests", description = "카테고리 지출에 대한 관심사별 지출 합계를 조회합니다.")
    @Override
    public ArrayList<ExpensePerInterest> getExpensePerInterests(Long userId, LocalDate start, LocalDate end) {
        return transactionHistoryDetailRepository.getExpensePerInterests(userId, start, end);
    }

    public TransactionHistoryDetailService(
            TransactionHistoryDetailRepository transactionHistoryDetailRepository,
            TransactionHistoryRepository transactionHistoryRepository,
            InterestRepository interestRepository) {
        this.transactionHistoryDetailRepository = transactionHistoryDetailRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.interestRepository = interestRepository;
    }
}
