package com.v.hana.usecase.transaction;

import com.v.hana.command.transaction.ReadTransactionHistoryByIdCommend;
import com.v.hana.command.transaction.ReadTransactionsCommand;
import com.v.hana.command.transaction.UpdateTransactionHistoryCommand;
import com.v.hana.entity.transaction.TransactionHistory;
import java.util.ArrayList;

public interface TransactionHistoryUseCase {
    TransactionHistory readTransactionHistoryById(
            ReadTransactionHistoryByIdCommend readTransactionHistoryByIdCommend);

    TransactionHistory updateTransactionHistory(
            UpdateTransactionHistoryCommand updateTransactionHistoryCommand);

    ArrayList<TransactionHistory> readTransactionHistory(
            ReadTransactionsCommand readTransactionsCommand);
}
