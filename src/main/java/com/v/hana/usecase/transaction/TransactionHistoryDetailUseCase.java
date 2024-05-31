package com.v.hana.usecase.transaction;

import com.v.hana.command.transaction.ReadTransactionHistoryDetailsByIdCommend;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import java.util.ArrayList;

public interface TransactionHistoryDetailUseCase {
    ArrayList<TransactionHistoryDetail> readTransactionHistoryDetailsById(
            ReadTransactionHistoryDetailsByIdCommend readTransactionHistoryByIdCommend);
}
