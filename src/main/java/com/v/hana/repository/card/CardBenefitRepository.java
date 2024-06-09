package com.v.hana.repository.card;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.card.CardBenefit;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "CardBenefitRepository", description = "카드 혜택 레포지토리 인터페이스")
@Repository
public interface CardBenefitRepository extends JpaRepository<CardBenefit, Long> {
    @Query(value = "SELECT * FROM card_benefits WHERE card_id = :cardId", nativeQuery = true)
    ArrayList<CardBenefit> findCardBenefitByCardId(@Param("cardId") Long cardId);
}
