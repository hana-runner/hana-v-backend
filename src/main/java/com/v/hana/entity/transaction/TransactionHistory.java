package com.v.hana.entity.transaction;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.BaseTimeEntity;
import com.v.hana.entity.account.Account;
import com.v.hana.entity.category.Category;
import com.v.hana.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "TransactionHistory", description = "거래내역 엔티티 클래스")
@Entity
@Table(name = "transaction_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @Column(name = "approval_number", nullable = false)
    private Integer approvalNumber;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "action", nullable = false, length = 10)
    private String action;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Builder
    public TransactionHistory(
            User user,
            Account account,
            Category category,
            Integer approvalNumber,
            String type,
            String description,
            String action,
            Long amount,
            Long balance) {
        this.user = user;
        this.account = account;
        this.category = category;
        this.approvalNumber = approvalNumber;
        this.type = type;
        this.description = description;
        this.action = action;
        this.amount = amount;
        this.balance = balance;
    }
}
