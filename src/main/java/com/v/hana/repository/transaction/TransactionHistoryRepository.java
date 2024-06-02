package com.v.hana.repository.transaction;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.transaction.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "TransactionHistoryRepository", description = "거래내역 레포지토리 클래스")
@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {}
