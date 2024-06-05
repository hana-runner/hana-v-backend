package com.v.hana.entity.account;

import com.v.hana.common.annotation.TypeInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Account", description = "계좌 API 엔티티 클래스")
@Entity
@Table(name = "account_api")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true, length = 255)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "bank_name", nullable = false, length = 50)
    private String bankName;

    @Column(name = "account_name", nullable = false, length = 50)
    private String accountName;

    @Column(name = "account_type", nullable = false, length = 50)
    private String accountType;

    @Builder
    public AccountApi(
            Long id,
            String accountNumber,
            Long balance,
            String bankName,
            String accountName,
            String accountType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.bankName = bankName;
        this.accountName = accountName;
        this.accountType = accountType;
    }
}
