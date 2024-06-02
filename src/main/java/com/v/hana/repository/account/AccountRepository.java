package com.v.hana.repository.account;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "AccountRepository", description = "계좌 레포지토리 인터페이스")
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {}
