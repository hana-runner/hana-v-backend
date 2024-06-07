package com.v.hana.repository.account;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.AccountDto;
import com.v.hana.entity.account.Account;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "AccountRepository", description = "계좌 레포지토리 인터페이스")
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    ArrayList<AccountDto> findByUserId(Long userId);

    Optional<Account> findByAccountNumber(String accountNumber);

    @Query(
            value = "UPDATE accounts a SET a.is_deleted = false WHERE a.id = :accountId",
            nativeQuery = true)
    @Modifying
    @Transactional
    void updateIsDeleted(Long accountId);
}
