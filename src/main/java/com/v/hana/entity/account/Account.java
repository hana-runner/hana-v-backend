package com.v.hana.entity.account;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.user.User;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@TypeInfo(name = "Account", description = "계좌 엔티티 클래스")
@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE accounts SET is_deleted = true WHERE id = ?")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "account_number", nullable = false, unique = true, length = 255)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "registered_at", nullable = false)
    private Timestamp registeredAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "bank_name", nullable = false, length = 50)
    private String bankName;

    @Column(name = "account_name", nullable = false, length = 50)
    private String accountName;

    @Column(name = "account_type", nullable = false, length = 50)
    private String accountType;

    @Builder
    public Account(
            User user,
            String accountNumber,
            Long balance,
            Timestamp registeredAt,
            String bankName,
            String accountName,
            String accountType) {
        this.user = user;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.registeredAt = registeredAt;
        this.bankName = bankName;
        this.accountName = accountName;
        this.accountType = accountType;
    }

    @PrePersist
    protected void onCreate() {
        if (this.registeredAt == null) {
            this.registeredAt = new Timestamp(System.currentTimeMillis());
        }
    }
}
