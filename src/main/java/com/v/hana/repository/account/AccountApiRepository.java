package com.v.hana.repository.account;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.account.AccountDto;
import com.v.hana.entity.account.AccountApi;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "AccountApiRepository", description = "계좌 API 레포지토리 인터페이스")
@Repository
public interface AccountApiRepository extends JpaRepository<AccountApi, Long> {
    Optional<AccountDto> findByAccountNumber(String accountNumber);
}
