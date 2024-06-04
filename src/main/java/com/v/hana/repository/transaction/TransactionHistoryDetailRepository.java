package com.v.hana.repository.transaction;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.interest.UserInterestTransactionDto;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @MethodInfo(name = "findAll", description = "거래내역 상세 목록을 조회합니다.")
    ArrayList<TransactionHistoryDetail> findAll();

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
                    "INSERT INTO transaction_history_details (transaction_history_id, description, amount, interest_id, user_id, created_at, updated_at, is_deleted) VALUES (:transactionHistoryId, :description, :amount, :interestId, :userId, :createdAt, :updatedAt, 0)",
            nativeQuery = true)
    void insertTransactionHistoryDetail(
            @Param("transactionHistoryId") Long transactionHistoryId,
            @Param("description") String description,
            @Param("amount") Long amount,
            @Param("interestId") Long interestId,
            @Param("userId") Long userId,
            @Param("createdAt") String createdAt,
            @Param("updatedAt") String updatedAt);
}
