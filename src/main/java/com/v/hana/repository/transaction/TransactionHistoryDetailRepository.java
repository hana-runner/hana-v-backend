package com.v.hana.repository.transaction;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.ExpensePerInterest;
import com.v.hana.dto.interest.UserComparison;
import com.v.hana.dto.interest.UserInterestTransactionDto;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@TypeInfo(name = "TransactionHistoryDetailRepository", description = "거래내역 상세 레포지토리 인터페이스")
@Repository
public interface TransactionHistoryDetailRepository
        extends JpaRepository<TransactionHistoryDetail, Long> {
    @MethodInfo(name = "findAllByTransactionHistoryId", description = "거래내역 ID로 거래내역 상세 목록을 조회합니다.")
    ArrayList<TransactionHistoryDetail> findAllByTransactionHistoryId(Long id);

    @Query(
            value =
                    "SELECT thd.transaction_history_id as TransactionHistoryId, thd.description, thd.amount, thd.interest_id as InterestId, th.created_at as CreatedAt, a.bank_name as BankName, a.account_number as AccountNumber "
                            + "FROM transaction_history_details thd "
                            + "JOIN transaction_histories th ON thd.transaction_history_id = th.id "
                            + "JOIN accounts a ON th.account_id = a.id "
                            + "WHERE thd.user_id = :userId AND thd.interest_id = :interestId AND YEAR(th.created_at) = :year AND MONTH(th.created_at) = :month",
            nativeQuery = true)
    ArrayList<UserInterestTransactionDto>
            findTransactionDetailsByUserIdAndInterestIdAndYearAndMonth(
                    Long userId, Long interestId, int year, int month);

    @Query(
            value =
                    "SELECT thd.* "
                            + "FROM hanaVdb.transaction_history_details thd "
                            + "JOIN hanaVdb.transaction_histories th ON thd.transaction_history_id = th.id "
                            + "WHERE th.account_id = :accountId "
                            + "AND th.created_at >= :startDate "
                            + "AND th.created_at <= :endDate "
                            + "ORDER BY th.created_at DESC",
            nativeQuery = true)
    ArrayList<TransactionHistoryDetail>
            findTransactionHistoryDetailsByAccountIdAndDateRangeOrderByDESC(
                    @Param("accountId") Long accountId,
                    @Param("startDate") String startDate,
                    @Param("endDate") String endDate);

    @Query(
            value =
                    "SELECT thd.* "
                            + "FROM hanaVdb.transaction_history_details thd "
                            + "JOIN hanaVdb.transaction_histories th ON thd.transaction_history_id = th.id "
                            + "WHERE th.account_id = :accountId "
                            + "AND th.created_at >= :startDate "
                            + "AND th.created_at <= :endDate "
                            + "ORDER BY th.created_at",
            nativeQuery = true)
    ArrayList<TransactionHistoryDetail>
            findTransactionHistoryDetailsByAccountIdAndDateRangeOrderByASC(
                    @Param("accountId") Long accountId,
                    @Param("startDate") String startDate,
                    @Param("endDate") String endDate);

    @MethodInfo(name = "deleteAllById", description = "거래내역 상세 ID로 거래내역 상세 목록을 삭제합니다.")
    @Modifying
    @Query(
            value = "DELETE FROM transaction_history_details WHERE transaction_history_id = :id",
            nativeQuery = true)
    void deleteAllByTransactionHistoryId(Long id);

    @MethodInfo(name = "insertTransactionHistoryDetail", description = "거래내역 상세를 추가합니다.")
    @Modifying
    @Query(
            value =
                    "INSERT INTO transaction_history_details (transaction_history_id, description, amount, interest_id, user_id) VALUES (:transactionHistoryId, :description, :amount, :interestId, :userId)",
            nativeQuery = true)
    void insertTransactionHistoryDetail(
            @Param("transactionHistoryId") Long transactionHistoryId,
            @Param("description") String description,
            @Param("amount") Long amount,
            @Param("interestId") Long interestId,
            @Param("userId") Long userId);

    @MethodInfo(
            name = "sumAmountByUserIdAndInterestId",
            description = "유저 ID와 관심사 ID로 거래내역 상세의 금액을 합산합니다.")
    @Query(
            value =
                    "SELECT SUM(transaction_history_details.amount) FROM transaction_history_details JOIN transaction_histories ON transaction_history_details.transaction_history_id = transaction_histories.id"
                            + " WHERE transaction_history_details.user_id = :userId AND interest_id = :interestId AND created_at >= :start AND created_at <= :end",
            nativeQuery = true)
    Long sumAmountByUserIdAndInterestId(
            Long userId, Long interestId, LocalDate start, LocalDate end);

    @MethodInfo(name = "sumAmountByInterestId", description = "관심사 ID로 거래내역 상세의 금액을 합산합니다.")
    @Query(
            value =
                    "SELECT SUM(transaction_history_details.amount) FROM transaction_history_details JOIN transaction_histories ON transaction_history_details.transaction_history_id = transaction_histories.id"
                            + " WHERE interest_id = :interestId AND created_at >= :start AND created_at <= :end",
            nativeQuery = true)
    Long sumAmountByInterestId(Long interestId, LocalDate start, LocalDate end);

    @MethodInfo(name = "getExpensePerInterests", description = "카테고리 지출에 대한 관심사별 지출 합계를 조회합니다.")
    @Query(
            value =
                    "WITH temp AS (SELECT u.id as user_id, a.id as account_id, th.category_id, c.title as category_title, th.id, th.type, th.created_at "
                            + "FROM transaction_histories th "
                            + "JOIN categories c ON c.id = th.category_id "
                            + "    JOIN users u ON u.id = th.user_id"
                            + "    JOIN accounts a ON a.id = th.account_id"
                            + "    WHERE th.type = '출금')\n"
                            + "SELECT u.id as userId, temp.account_id as accountId, temp.category_title as categoryTitle, i.id as interestId, thd.transaction_history_id as transactionHistoryId, i.title as interestTitle, i.color as interestColor, "
                            + "SUM(thd.amount) as expense, temp.created_at as createdAt "
                            + "FROM transaction_history_details thd "
                            + "JOIN users u ON u.id = thd.user_id "
                            + "JOIN interests i ON i.id = thd.interest_id "
                            + "JOIN temp ON temp.id = thd.transaction_history_id "
                            + "WHERE u.id = :userId "
                            + "AND temp.created_at >= :start AND temp.created_at <= :end "
                            + "GROUP BY i.id, temp.category_id",
            nativeQuery = true)
    ArrayList<ExpensePerInterest> getExpensePerInterests(
            Long userId, LocalDate start, LocalDate end);

    @MethodInfo(name = "getComparison", description = "관심사별 카테고리 지출 비교 정보를 조회합니다.")
    @Query(
            value =
                    "SELECT thd.interest_id AS interestId, i.title AS interestTitle, c.title AS categoryTitle, th.category_id AS categoryId, "
                            + "SUM(thd.amount) OVER(PARTITION BY th.category_id, thd.interest_id) AS expense,"
                            + "ROUND(AVG(thd.amount) OVER(PARTITION BY th.category_id, thd.interest_id), 0) AS average,"
                            + "(thd.amount - ROUND(AVG(thd.amount) OVER(PARTITION BY th.category_id, thd.interest_id), 0)) AS difference\n"
                            + "FROM transaction_history_details thd\n"
                            + "JOIN transaction_histories th ON th.id = thd.transaction_history_id\n"
                            + "JOIN interests i ON thd.interest_id = i.id\n"
                            + "JOIN categories c ON th.category_id = c.id\n"
                            + "WHERE thd.user_id in (SELECT u.id FROM users u WHERE TIMESTAMPDIFF(YEAR, u.birthday, CURDATE()) BETWEEN :begin AND :finish AND u.gender = 1) \n"
                            + "AND thd.interest_id in (SELECT ui.interest_id FROM user_interests ui WHERE ui.user_id = :userId) \n"
                            + "AND th.type = '출금' "
                            + "AND thd.interest_id = :interestId "
                            + "AND YEAR(th.created_at) = :year AND MONTH(th.created_at) = :month "
                            + "ORDER BY expense DESC",
            nativeQuery = true)
    ArrayList<UserComparison> getComparison(
            Long userId, Long interestId, int begin, int finish, int year, int month);

    @MethodInfo(name = "deleteByUserIdAndInterestId", description = "사용자 관심사를 삭제합니다.")
    @Modifying
    @Transactional
    @Query(
            "DELETE FROM TransactionHistoryDetail thd WHERE thd.user.id = :userId AND thd.interest.id = :interestId")
    void deleteByUserIdAndInterestId(
            @Param("userId") Long userId, @Param("interestId") Long interestId);
}
