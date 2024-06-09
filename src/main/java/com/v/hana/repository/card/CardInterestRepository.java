package com.v.hana.repository.card;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.entity.card.Card;
import com.v.hana.entity.card.CardInterest;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "CardInterestRepository", description = "카드 관심사 레포지토리 인터페이스")
@Repository
public interface CardInterestRepository extends JpaRepository<CardInterest, Long> {
    @Query(
            value = "SELECT c.card FROM CardInterest c WHERE c.interest.id = :interestId",
            nativeQuery = false)
    ArrayList<Card> findByInterestId(@Param("interestId") Long interestId);
}
