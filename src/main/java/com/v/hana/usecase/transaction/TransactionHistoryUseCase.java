package com.v.hana.usecase.transaction;

import com.v.hana.command.transaction.ReadTransactionHistoryByIdCommend;
import com.v.hana.command.transaction.UpdateTransactionHistoryCommand;
import com.v.hana.entity.transaction.TransactionHistory;

public interface TransactionHistoryUseCase {
    TransactionHistory readTransactionHistoryById(
            ReadTransactionHistoryByIdCommend readTransactionHistoryByIdCommend);

    TransactionHistory updateTransactionHistory(
            UpdateTransactionHistoryCommand updateTransactionHistoryCommand);
}
