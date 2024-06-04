package com.v.hana.service.interest;

import com.v.hana.command.interest.GetUserInterestTransactionsCommand;
import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.interest.*;
import com.v.hana.entity.interest.Interest;
import com.v.hana.repository.interest.InterestRepository;
import com.v.hana.repository.interest.UserInterestRepository;
import com.v.hana.repository.transaction.TransactionHistoryDetailRepository;
import com.v.hana.repository.transaction.TransactionHistoryRepository;
import com.v.hana.usecase.interest.UserInterestUseCase;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@TypeInfo(name = "UserInterestService", description = "사용자 관심사 서비스")
@Service
public class UserInterestService implements UserInterestUseCase {
    private final InterestRepository interestRepository;

    private final UserInterestRepository userInterestRepository;

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final TransactionHistoryDetailRepository transactionHistoryDetailRepository;

    @MethodInfo(name = "getUserInterests", description = "유저의 관심사를 조회합니다.")
    @Override
    public UserInterestResponse getUserInterests(GetUserInterestsCommand command) {
        return UserInterestResponse.builder()
                .data(
                        userInterestRepository.findByUserId(command.getUserId()).stream()
                                .map(
                                        userInterest -> {
                                            Interest interest =
                                                    interestRepository
                                                            .findById(userInterest.getInterestId())
                                                            .orElseThrow(
                                                                    () ->
                                                                            new RuntimeException(
                                                                                    "Interest not found"));
                                            return UserInterestDto.builder()
                                                    .interestId(userInterest.getInterestId())
                                                    .title(interest.getTitle())
                                                    .subtitle(userInterest.getSubtitle())
                                                    .imageUrl(userInterest.getImageUrl())
                                                    .color(interest.getColor())
                                                    .build();
                                        })
                                .collect(Collectors.toCollection(ArrayList::new)))
                .build();
    }

    @MethodInfo(name = "getUserInterestTransactions", description = "유저의 관심사별 거래 내역을 조회합니다.")
    @Override
    public UserInterestTransactionsResponse getUserInterestTransactions(
            GetUserInterestTransactionsCommand command) {
        ArrayList<UserInterestTransactionDto> transactions =
                transactionHistoryDetailRepository
                        .findTransactionDetailsByUserIdAndInterestIdAndYearAndMonth(
                                command.getUserId(),
                                command.getInterestId(),
                                command.getYear(),
                                command.getMonth());
        Long totalSpent =
                transactionHistoryRepository.findUserSpendingByDate(
                        command.getUserId(), command.getYear(), command.getMonth());
        int interestTotalSpent =
                transactions.stream()
                        .mapToInt(transaction -> transaction.getAmount().intValue())
                        .sum();

        UserInterestTransactionsDto userInterestTransactionsDto =
                UserInterestTransactionsDto.builder()
                        .transaction(transactions)
                        .totalSpent(totalSpent)
                        .interestTotalSpent(interestTotalSpent)
                        .build();

        return UserInterestTransactionsResponse.builder().data(userInterestTransactionsDto).build();
    }

    public UserInterestService(
            InterestRepository interestRepository,
            UserInterestRepository userInterestRepository,
            TransactionHistoryRepository transactionHistoryRepository,
            TransactionHistoryDetailRepository transactionHistoryDetailRepository) {
        this.interestRepository = interestRepository;
        this.userInterestRepository = userInterestRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistoryDetailRepository = transactionHistoryDetailRepository;
    }
}
