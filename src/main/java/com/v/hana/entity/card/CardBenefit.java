package com.v.hana.entity.card;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card_benefits")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardBenefit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "group", nullable = false, length = 50)
    private String group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Builder
    public CardBenefit(String title, String description, String group, Card card) {
        this.title = title;
        this.description = description;
        this.group = group;
        this.card = card;
    }
}
