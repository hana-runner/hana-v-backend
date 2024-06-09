package com.v.hana.repository.card;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "CardRepository", description = "카드 레포지토리 인터페이스")
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
