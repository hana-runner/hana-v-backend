package com.v.hana.service.transaction;

import com.v.hana.command.transaction.ReadTransactionHistoryByIdCommend;
import com.v.hana.command.transaction.UpdateTransactionHistoryCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.transaction.TransactionHistory;
import com.v.hana.exception.transaction.TransactionIdNotFoundException;
import com.v.hana.repository.transaction.TransactionHistoryRepository;
import com.v.hana.usecase.transaction.TransactionHistoryUseCase;
import org.springframework.stereotype.Service;

@TypeInfo(name = "TransactionHistoryService", description = "거래내역 서비스 클래스")
@Service
public class TransactionHistoryService implements TransactionHistoryUseCase {
    private final TransactionHistoryRepository transactionHistoryRepository;

    @MethodInfo(name = "readTransactionHistoryById", description = "거래내역 ID로 거래내역을 조회합니다.")
    @Override
    public TransactionHistory readTransactionHistoryById(
            ReadTransactionHistoryByIdCommend command) {

        return transactionHistoryRepository
                .findById(command.getId())
                .orElseThrow(TransactionIdNotFoundException::new);
    }

    @MethodInfo(name = "updateTransactionHistory", description = "거래내역 상세를 수정합니다.")
    @Override
    public TransactionHistory updateTransactionHistory(UpdateTransactionHistoryCommand command) {
        TransactionHistory transactionHistory =
                transactionHistoryRepository
                        .findById(command.getId())
                        .orElseThrow(TransactionIdNotFoundException::new);

        return transactionHistoryRepository.save(
                TransactionHistory.builder()
                        .user(transactionHistory.getUser())
                        .account(transactionHistory.getAccount())
                        .category(command.getCategory())
                        .approvalNumber(transactionHistory.getApprovalNumber())
                        .type(transactionHistory.getType())
                        .description(transactionHistory.getDescription())
                        .action(transactionHistory.getAction())
                        .amount(transactionHistory.getAmount())
                        .balance(transactionHistory.getBalance())
                        .build());
    }

    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }
}
