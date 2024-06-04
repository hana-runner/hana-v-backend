package com.v.hana.repository.transaction;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.transaction.TransactionHistory;
import java.util.ArrayList;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "TransactionHistoryRepository", description = "거래내역 레포지토리 클래스")
@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    @Query(
            value =
                    "SELECT SUM(amount) as totalPriceByDate "
                            + "FROM transaction_histories "
                            + "WHERE user_id = :userId AND YEAR(created_at) = :year AND MONTH(created_at) = :month",
            nativeQuery = true)
    Long findUserSpendingByDate(Long userId, int year, int month);

    ArrayList<TransactionHistory> findAll(Sort sort);
}
