package com.v.hana.event.transaction;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.transaction.InterestDto;
import com.v.hana.entity.interest.Interest;
import com.v.hana.usecase.interest.InterestUseCase;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@TypeInfo(name = "ReadInterestsEventListener", description = "관심사 이벤트 리스너 클래스")
@Component
public class ReadInterestsEventListener {
    private final InterestUseCase interestUseCase;

    @MethodInfo(name = "handle", description = "관심사 목록을 조회합니다.")
    @TransactionalEventListener(fallbackExecution = true)
    public ArrayList<Interest> handle(ReadInterestsEvent readInterestsEvent) {
        return interestUseCase.getInterestsById(
                readInterestsEvent.getInterests().stream()
                        .map(InterestDto::getId)
                        .collect(Collectors.toCollection(ArrayList::new)));
    }

    @Builder
    public ReadInterestsEventListener(InterestUseCase interestUseCase) {
        this.interestUseCase = interestUseCase;
    }
}
