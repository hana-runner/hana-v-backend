package com.v.hana.repository.card;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.card.CardBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@TypeInfo(name = "CardBenefitRepository", description = "카드 혜택 레포지토리 인터페이스")
@Repository
public interface CardBenefitRepository extends JpaRepository<CardBenefit, Long> {
    ArrayList<CardBenefit> findCardBenefitByCardId(Long cardId);
}
