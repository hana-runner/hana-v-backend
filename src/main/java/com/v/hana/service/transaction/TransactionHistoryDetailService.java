package com.v.hana.service.transaction;

import com.v.hana.command.transaction.ReadTransactionHistoryDetailsByIdCommend;
import com.v.hana.command.transaction.ReadTransactionsCommand;
import com.v.hana.command.transaction.UpdateTransactionHistoryDetailCommend;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.transaction.InterestDto;
import com.v.hana.entity.transaction.TransactionHistory;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import com.v.hana.exception.transaction.TransactionIdNotFoundException;
import com.v.hana.repository.interest.InterestRepository;
import com.v.hana.repository.transaction.TransactionHistoryDetailRepository;
import com.v.hana.repository.transaction.TransactionHistoryRepository;
import com.v.hana.usecase.transaction.TransactionHistoryDetailUseCase;
import jakarta.transaction.Transactional;
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

    public TransactionHistoryDetailService(
            TransactionHistoryDetailRepository transactionHistoryDetailRepository,
            TransactionHistoryRepository transactionHistoryRepository,
            InterestRepository interestRepository) {
        this.transactionHistoryDetailRepository = transactionHistoryDetailRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.interestRepository = interestRepository;
    }
}
