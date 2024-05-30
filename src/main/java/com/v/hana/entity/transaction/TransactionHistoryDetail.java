package com.v.hana.entity.transaction;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.interest.Interest;
import com.v.hana.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "TransactionHistoryDetail", description = "거래내역 상세 엔티티 클래스")
@Entity
@Table(name = "transaction_history_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionHistoryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_history_id", referencedColumnName = "id", nullable = false)
    private TransactionHistory transactionHistory;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "interest_id", referencedColumnName = "id", nullable = false)
    private Interest interest;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Builder
    public TransactionHistoryDetail(
            TransactionHistory transactionHistory,
            User user,
            Interest interest,
            String description,
            Long amount) {
        this.transactionHistory = transactionHistory;
        this.user = user;
        this.interest = interest;
        this.description = description;
        this.amount = amount;
    }
}
