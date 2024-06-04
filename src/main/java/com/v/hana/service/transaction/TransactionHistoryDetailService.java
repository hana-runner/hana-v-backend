package com.v.hana.service.transaction;

import com.v.hana.command.transaction.ReadTransactionHistoryDetailsByIdCommend;
import com.v.hana.command.transaction.ReadTransactionsCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import com.v.hana.exception.transaction.TransactionIdNotFoundException;
import com.v.hana.repository.transaction.TransactionHistoryDetailRepository;
import com.v.hana.usecase.transaction.TransactionHistoryDetailUseCase;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@TypeInfo(name = "TransactionHistoryDetailService", description = "거래내역 상세 서비스 클래스")
@Service
public class TransactionHistoryDetailService implements TransactionHistoryDetailUseCase {
    private final TransactionHistoryDetailRepository transactionHistoryDetailRepository;

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

    public TransactionHistoryDetailService(
            TransactionHistoryDetailRepository transactionHistoryDetailRepository) {
        this.transactionHistoryDetailRepository = transactionHistoryDetailRepository;
    }
}
