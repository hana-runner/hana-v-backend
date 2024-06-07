package com.v.hana.usecase.transaction;

import com.v.hana.command.transaction.ReadTransactionHistoryDetailsByIdCommend;
import com.v.hana.command.transaction.ReadTransactionsCommand;
import com.v.hana.command.transaction.UpdateTransactionHistoryDetailCommend;
import com.v.hana.dto.account.ExpensePerInterest;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import java.time.LocalDate;
import java.util.ArrayList;

public interface TransactionHistoryDetailUseCase {
    ArrayList<TransactionHistoryDetail> readTransactionHistoryDetailsById(
            ReadTransactionHistoryDetailsByIdCommend readTransactionHistoryByIdCommend);

    ArrayList<TransactionHistoryDetail> readTransactionHistoryDetails(
            ReadTransactionsCommand readTransactionsCommand);

    ArrayList<TransactionHistoryDetail> updateTransactionHistoryDetails(
            UpdateTransactionHistoryDetailCommend updateTransactionHistoryDetailCommend);

    Long sumAmountByUserIdAndInterestId(
            Long userId, Long interestId, LocalDate start, LocalDate end);

    Long sumAmountByInterestId(Long interestId, LocalDate start, LocalDate end);

    ArrayList<ExpensePerInterest> getExpensePerInterests(
            Long userId, LocalDate start, LocalDate end);
}
