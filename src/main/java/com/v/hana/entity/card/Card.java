package com.v.hana.entity.card;

import com.v.hana.common.annotation.TypeInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Card", description = "카드 엔티티 클래스")
@Entity
@Table(name = "cards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @Builder
    public Card(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
