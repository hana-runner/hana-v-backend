package com.v.hana.repository.transaction;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.ExpensePerCategory;
import com.v.hana.entity.transaction.TransactionHistory;

import java.time.LocalDate;
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

    @Query(value = "SELECT th.account_id as accountId, th.category_id as categoryId, c.title, c.color, SUM(th.amount) as expense " +
            "FROM transaction_histories th " +
            "JOIN categories c ON c.id = th.category_id " +
            "JOIN users u ON u.id = th.user_id " +
            "JOIN accounts a ON a.id = th.account_id " +
            "WHERE th.user_id = :userId " +
            "AND th.type = '출금' " +
            "AND th.created_at BETWEEN :start AND :end " +
            "GROUP BY th.category_id", nativeQuery = true)
    ArrayList<ExpensePerCategory> getExpensePerCategories(Long userId, LocalDate start, LocalDate end);

}
