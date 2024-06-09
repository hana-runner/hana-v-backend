package com.v.hana.event.interest;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.interest.UserInterestReportsDto;
import com.v.hana.dto.interest.UserInterestReportsResponse;
import com.v.hana.usecase.transaction.TransactionHistoryDetailUseCase;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@TypeInfo(name = "SumAmountEventListener", description = "금액 합산 이벤트 리스너")
@Component
public class SumAmountEventListener {
    private final TransactionHistoryDetailUseCase transactionHistoryDetailUseCase;

    @MethodInfo(name = "handle", description = "금액 합산 이벤트를 처리합니다.")
    @TransactionalEventListener(fallbackExecution = true)
    public UserInterestReportsResponse handle(SumAmountEvent sumAmountEvent) {
        Long userId = sumAmountEvent.getUserId();

        LocalDate end =
                LocalDate.of(sumAmountEvent.getYear(), sumAmountEvent.getMonth(), 1)
                        .with(TemporalAdjusters.lastDayOfMonth());

        if (end.isAfter(LocalDate.now())) {
            end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }

        LocalDate start = end.minusMonths(6).withDayOfMonth(1);

        Long myAverage =
                transactionHistoryDetailUseCase.sumAmountByUserIdAndInterestId(
                        userId, sumAmountEvent.getInterestId(), start, end);

        if (myAverage == null) {
            myAverage = 0L;
        }

        Long peerAverage =
                transactionHistoryDetailUseCase.sumAmountByInterestId(
                        sumAmountEvent.getInterestId(), start, end);

        if (peerAverage == null) {
            peerAverage = 0L;
        }

        ArrayList<Long> myMonth = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            LocalDate monthStart = end.minusMonths(i).withDayOfMonth(1);
            LocalDate monthEnd = monthStart.with(TemporalAdjusters.lastDayOfMonth());

            Long sumAmount =
                    transactionHistoryDetailUseCase.sumAmountByUserIdAndInterestId(
                            userId, sumAmountEvent.getInterestId(), monthStart, monthEnd);

            if (sumAmount == null) {
                sumAmount = 0L;
            }

            myMonth.add(sumAmount);
        }

        return UserInterestReportsResponse.builder()
                .data(
                        UserInterestReportsDto.builder()
                                .myMonth(myMonth)
                                .myAverage(myAverage / 6)
                                .peerAverage(peerAverage / 6)
                                .build())
                .build();
    }

    @Builder
    public SumAmountEventListener(TransactionHistoryDetailUseCase transactionHistoryDetailUseCase) {
        this.transactionHistoryDetailUseCase = transactionHistoryDetailUseCase;
    }
}
