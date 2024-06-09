package com.v.hana.entity.card;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.interest.Interest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "CardInterest", description = "카드 관심사 엔티티 클래스")
@Entity
@Table(name = "card_interests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id", nullable = false)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "interest_id", referencedColumnName = "id", nullable = false)
    private Interest interest;

    @Builder
    public CardInterest(Card card, Interest interest) {
        this.card = card;
        this.interest = interest;
    }
}
