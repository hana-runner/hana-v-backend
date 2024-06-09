package com.v.hana.usecase.card;

import com.v.hana.command.card.GetUserInterestCardsCommand;
import com.v.hana.dto.card.CardInterestResponse;

public interface CardInterestUseCase {
    CardInterestResponse getCardInterest(GetUserInterestCardsCommand command);
}
