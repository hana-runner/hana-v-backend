package com.v.hana.event.interest;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.interest.UserInterestReportsResponse;
import com.v.hana.usecase.transaction.TransactionHistoryDetailUseCase;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.util.ArrayList;

@TypeInfo(name = "SumAmountEventListener", description = "금액 합산 이벤트 리스너")
@Component
public class SumAmountEventListener {
    private final TransactionHistoryDetailUseCase transactionHistoryDetailUseCase;

    @MethodInfo(name = "handle", description = "금액 합산 이벤트를 처리합니다.")
    @TransactionalEventListener(fallbackExecution = true)
    public UserInterestReportsResponse handle(SumAmountEvent sumAmountEvent) {
        Long userId = sumAmountEvent.getUserId();

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusMonths(6).withDayOfMonth(1);

        Long myAverage = transactionHistoryDetailUseCase
                .sumAmountByUserIdAndInterestId(userId, sumAmountEvent.getInterestId(), start, end);
        Long peerAverage = transactionHistoryDetailUseCase
                .sumAmountByInterestId(sumAmountEvent.getInterestId(), start, end);

        ArrayList<Long> myMouth = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            start = end.minusMonths(i).withDayOfMonth(1);
            end = end.minusMonths(i - 1).withDayOfMonth(1);

            Long sumAmount = transactionHistoryDetailUseCase
                    .sumAmountByUserIdAndInterestId(userId, sumAmountEvent.getInterestId(), start, end);

            if (sumAmount == null) {
                sumAmount = 0L;
            }

            myMouth.add(sumAmount);
        }

        return UserInterestReportsResponse.builder()
                .myMouth(myMouth)
                .myAverage(myAverage / 6)
                .peerAverage(peerAverage / 6)
                .build();
    }


    @Builder
    public SumAmountEventListener(TransactionHistoryDetailUseCase transactionHistoryDetailUseCase) {
        this.transactionHistoryDetailUseCase = transactionHistoryDetailUseCase;
    }
}
