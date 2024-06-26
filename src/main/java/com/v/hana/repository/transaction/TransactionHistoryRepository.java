package com.v.hana.repository.transaction;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.ExpensePerCategory;
import com.v.hana.entity.transaction.TransactionHistory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    Optional<Long> findUserSpendingByDate(Long userId, int year, int month);

    @Query(
            value =
                    "SELECT th.* "
                            + "FROM hanaVdb.transaction_histories th "
                            + "WHERE th.account_id = :accountId "
                            + "AND th.created_at >= :startDate "
                            + "AND th.created_at <= :endDate "
                            + "ORDER BY th.created_at DESC",
            nativeQuery = true)
    ArrayList<TransactionHistory> findTransactionHistoriesByAccountIdAndDateRangeOrderByDESC(
            @Param("accountId") Long accountId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);

    @Query(
            value =
                    "SELECT th.* "
                            + "FROM hanaVdb.transaction_histories th "
                            + "WHERE th.account_id = :accountId "
                            + "AND th.created_at >= :startDate "
                            + "AND th.created_at <= :endDate "
                            + "ORDER BY th.created_at",
            nativeQuery = true)
    ArrayList<TransactionHistory> findTransactionHistoriesByAccountIdAndDateRangeOrderByASC(
            @Param("accountId") Long accountId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);

    @MethodInfo(name = "updateCategory", description = "거래내역 카테고리를 수정합니다.")
    @Modifying
    @Query(
            value =
                    "UPDATE transaction_histories SET category_id = :categoryId WHERE id = :transactionHistoryId",
            nativeQuery = true)
    void updateCategory(Long transactionHistoryId, Long categoryId);

    @MethodInfo(name = "getExpensePerCategories", description = "카테고리별 지출 합계를 조회합니다.")
    @Query(
            value =
                    "SELECT th.account_id as accountId, th.category_id as categoryId, c.title, c.color, SUM(th.amount) as expense "
                            + "FROM transaction_histories th "
                            + "JOIN categories c ON c.id = th.category_id "
                            + "JOIN users u ON u.id = th.user_id "
                            + "JOIN accounts a ON a.id = th.account_id "
                            + "WHERE th.user_id = :userId "
                            + "AND th.action = '출금' "
                            + "AND th.created_at >= :start AND th.created_at <= :end "
                            + "GROUP BY th.category_id "
                            + "ORDER BY expense DESC",
            nativeQuery = true)
    ArrayList<ExpensePerCategory> getExpensePerCategories(
            Long userId, LocalDate start, LocalDate end);
}
