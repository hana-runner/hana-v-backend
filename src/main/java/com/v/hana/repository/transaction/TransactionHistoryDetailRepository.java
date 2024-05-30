package com.v.hana.repository.transaction;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.transaction.TransactionHistoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "TransactionHistoryDetailRepository", description = "거래내역 상세 레포지토리 인터페이스")
@Repository
public interface TransactionHistoryDetailRepository
        extends JpaRepository<TransactionHistoryDetail, Long> {}
