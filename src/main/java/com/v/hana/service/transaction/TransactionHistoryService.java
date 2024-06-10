package com.v.hana.service.transaction;

import com.v.hana.command.transaction.ReadTransactionHistoryByIdCommend;
import com.v.hana.command.transaction.ReadTransactionsCommand;
import com.v.hana.command.transaction.UpdateTransactionHistoryCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.transaction.TransactionHistory;
import com.v.hana.exception.transaction.TransactionIdNotFoundException;
import com.v.hana.repository.transaction.TransactionHistoryRepository;
import com.v.hana.usecase.transaction.TransactionHistoryUseCase;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
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
    @Transactional
    public TransactionHistory updateTransactionHistory(UpdateTransactionHistoryCommand command) {
        transactionHistoryRepository.updateCategory(command.getId(), command.getCategory().getId());
        return transactionHistoryRepository.findById(command.getId()).get();
    }

    @MethodInfo(name = "readTransactionHistory", description = "거래내역 목록을 조회합니다.")
    @Override
    public ArrayList<TransactionHistory> readTransactionHistory(
            ReadTransactionsCommand readTransactionsCommand) {
        if (readTransactionsCommand.getSort()) {
            return transactionHistoryRepository
                    .findTransactionHistoriesByAccountIdAndDateRangeOrderByDESC(
                            readTransactionsCommand.getAccount().getId(),
                            readTransactionsCommand.getStart().toString(),
                            readTransactionsCommand.getEnd().toString());
        } else {
            return transactionHistoryRepository
                    .findTransactionHistoriesByAccountIdAndDateRangeOrderByASC(
                            readTransactionsCommand.getAccount().getId(),
                            readTransactionsCommand.getStart().toString(),
                            readTransactionsCommand.getEnd().toString());
        }
    }

    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }
}
