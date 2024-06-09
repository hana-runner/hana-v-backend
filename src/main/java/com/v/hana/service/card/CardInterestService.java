package com.v.hana.service.card;

import com.v.hana.command.card.GetUserInterestCardsCommand;
import com.v.hana.dto.card.CardBenefitDto;
import com.v.hana.dto.card.CardDto;
import com.v.hana.dto.card.CardInterestResponse;
import com.v.hana.repository.card.CardBenefitRepository;
import com.v.hana.repository.card.CardInterestRepository;
import com.v.hana.usecase.card.CardInterestUseCase;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CardInterestService implements CardInterestUseCase {
    private final CardInterestRepository cardInterestRepository;
    private final CardBenefitRepository cardBenefitRepository;

    @Override
    public CardInterestResponse getCardInterest(GetUserInterestCardsCommand command) {
        return CardInterestResponse.builder()
                .data(
                        cardInterestRepository.findByInterestId(command.getInterestId()).stream()
                                .map(
                                        card ->
                                                CardDto.builder()
                                                        .id(card.getId())
                                                        .name(card.getName())
                                                        .image(card.getImage())
                                                        .cardBenefits(
                                                                cardBenefitRepository
                                                                        .findCardBenefitByCardId(
                                                                                card.getId())
                                                                        .stream()
                                                                        .map(
                                                                                cardBenefit ->
                                                                                        CardBenefitDto
                                                                                                .builder()
                                                                                                .title(
                                                                                                        cardBenefit
                                                                                                                .getTitle())
                                                                                                .description(
                                                                                                        cardBenefit
                                                                                                                .getDescription())
                                                                                                .group(
                                                                                                        cardBenefit
                                                                                                                .getGroup())
                                                                                                .build())
                                                                        .collect(
                                                                                Collectors
                                                                                        .toCollection(
                                                                                                ArrayList
                                                                                                        ::new)))
                                                        .build())
                                .collect(Collectors.toCollection(ArrayList::new)))
                .build();
    }

    public CardInterestService(
            CardInterestRepository cardInterestRepository,
            CardBenefitRepository cardBenefitRepository) {
        this.cardInterestRepository = cardInterestRepository;
        this.cardBenefitRepository = cardBenefitRepository;
    }
}
