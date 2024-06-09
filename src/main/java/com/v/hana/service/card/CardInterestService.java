package com.v.hana.service.card;

import com.v.hana.command.card.GetUserInterestCardsCommand;
import com.v.hana.dto.card.CardInterestResponse;
import com.v.hana.usecase.card.CardInterestUseCase;
import org.springframework.stereotype.Service;

@Service
public class CardInterestService implements CardInterestUseCase {
    @Override
    public CardInterestResponse getCardInterest(GetUserInterestCardsCommand command) {
        return null;
    }
}
