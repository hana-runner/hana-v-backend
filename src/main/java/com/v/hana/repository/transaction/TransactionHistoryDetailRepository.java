package com.v.hana.repository.transaction;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.interest.UserInterestTransactionDto;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "TransactionHistoryDetailRepository", description = "거래내역 상세 레포지토리 인터페이스")
@Repository
public interface TransactionHistoryDetailRepository
        extends JpaRepository<TransactionHistoryDetail, Long> {
    ArrayList<TransactionHistoryDetail> findAllByTransactionHistoryId(Long id);

    @Query(value = "SELECT thd.transaction_history_id as TransactionHistoryId, thd.description, thd.amount, thd.interest_id as InterestId, th.created_at as CreatedAt, a.bank_name as BankName, a.account_number as AccountNumber " +
            "FROM transaction_history_details thd " +
            "JOIN transaction_histories th ON thd.transaction_history_id = th.id " +
            "JOIN accounts a ON th.account_id = a.id " +
            "WHERE thd.user_id = :userId AND thd.interest_id = :interestId AND YEAR(th.created_at) = :year AND MONTH(th.created_at) = :month", nativeQuery = true)
    ArrayList<UserInterestTransactionDto> findTransactionDetailsByUserIdAndInterestIdAndYearAndMonth(Long userId, Long interestId, int year, int month);

}

