package com.v.hana.repository.card;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.card.Card;
import com.v.hana.entity.card.CardInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@TypeInfo(name = "CardInterestRepository", description = "카드 관심사 레포지토리 인터페이스")
@Repository
public interface CardInterestRepository extends JpaRepository<CardInterest, Long> {
    @Query("SELECT c FROM CardInterest c JOIN c.card WHERE c.interest.id = :interestId")
    ArrayList<Card> findByInterestId(@Param("interestId") Long interestId);
}
