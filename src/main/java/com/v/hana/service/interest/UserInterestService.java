package com.v.hana.service.interest;

import com.v.hana.command.interest.AddUserInterestCommand;
import com.v.hana.command.interest.GetUserInterestReportsCommand;
import com.v.hana.command.interest.GetUserInterestTransactionsCommand;
import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.command.interest.ModifyUserInterestCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.response.PostSuccessResponse;
import com.v.hana.common.response.PutSuccessResponse;
import com.v.hana.dto.interest.*;
import com.v.hana.entity.interest.Interest;
import com.v.hana.entity.interest.UserInterest;
import com.v.hana.event.interest.SumAmountEvent;
import com.v.hana.event.interest.SumAmountEventListener;
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

    private final SumAmountEventListener sumAmountEventListener;

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
                                                            .findById(
                                                                    userInterest
                                                                            .getInterest()
                                                                            .getId())
                                                            .orElseThrow(
                                                                    () ->
                                                                            new RuntimeException(
                                                                                    "Interest not found"));
                                            return UserInterestDto.builder()
                                                    .interestId(userInterest.getInterest().getId())
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

        ArrayList<Interest> interestInfo =
                interestRepository.findTitleAndColorAndById(command.getInterestId());

        Long totalSpent =
                transactionHistoryRepository
                        .findUserSpendingByDate(
                                command.getUserId(), command.getYear(), command.getMonth())
                        .orElse(0L);

        int interestTotalSpent =
                transactions.stream()
                        .mapToInt(transaction -> transaction.getAmount().intValue())
                        .sum();

        UserInterestTransactionsDto userInterestTransactionsDto =
                UserInterestTransactionsDto.builder()
                        .transaction(transactions)
                        .totalSpent(totalSpent)
                        .interestTotalSpent(interestTotalSpent)
                        .title(interestInfo.get(0).getTitle())
                        .color(interestInfo.get(0).getColor())
                        .build();

        return UserInterestTransactionsResponse.builder().data(userInterestTransactionsDto).build();
    }

    @MethodInfo(name = "getUserInterestReports", description = "유저의 관심사별 거래 내역 리포트를 조회합니다.")
    @Override
    public UserInterestReportsResponse getUserInterestReports(
            GetUserInterestReportsCommand getUserInterestReportsCommand) {
        return sumAmountEventListener.handle(
                SumAmountEvent.builder()
                        .userId(getUserInterestReportsCommand.getUserId())
                        .interestId(getUserInterestReportsCommand.getInterestId())
                        .year(getUserInterestReportsCommand.getYear())
                        .month(getUserInterestReportsCommand.getMonth())
                        .build());
    }

    @MethodInfo(name = "addUserInterest", description = "사용자 관심사를 추가합니다.")
    @Override
    public PostSuccessResponse addUserInterest(AddUserInterestCommand command) {

        UserInterest userInterest =
                UserInterest.builder()
                        .user(command.getUser())
                        .interest(command.getInterest())
                        .subtitle(command.getSubtitle())
                        .imageUrl(command.getImage())
                        .build();
        userInterestRepository.save(userInterest);
        return PostSuccessResponse.builder().build();
    }

    @MethodInfo(name = "modifyUserInterest", description = "사용자 관심사를 수정합니다.")
    @Override
    public PutSuccessResponse modifyUserInterest(ModifyUserInterestCommand command) {
        userInterestRepository.updateUserInterest(
                command.getUser().getId(),
                command.getInterest().getId(),
                command.getSubtitle(),
                command.getImage());
        return PutSuccessResponse.builder().build();
    }

    @MethodInfo(name = "getComparison", description = "관심사별 카테고리 지출 비교 정보를 조회합니다.")
    @Override
    public UserCompareResponse getComparison(
            Long userId, Long interestId, int age, int year, int month) {

        int begin = 0;
        int finish = 0;

        if (age >= 0 && age <= 3) {
            begin = 0;
            finish = 3;
        } else if (age >= 4 && age <= 6) {
            begin = 4;
            finish = 6;
        } else if (age >= 7 && age <= 9) {
            begin = 7;
            finish = 9;
        } else if (age >= 10 && age <= 13) {
            begin = 10;
            finish = 13;
        } else if (age >= 14 && age <= 16) {
            begin = 14;
            finish = 16;
        } else if (age >= 17 && age <= 19) {
            begin = 17;
            finish = 19;
        } else if (age >= 20 && age <= 23) {
            begin = 20;
            finish = 23;
        } else if (age >= 24 && age <= 26) {
            begin = 24;
            finish = 26;
        } else if (age >= 27 && age <= 29) {
            begin = 27;
            finish = 29;
        } else if (age >= 30 && age <= 33) {
            begin = 30;
            finish = 33;
        } else if (age >= 34 && age <= 36) {
            begin = 34;
            finish = 36;
        } else if (age >= 37 && age <= 39) {
            begin = 37;
            finish = 39;
        } else if (age >= 40 && age <= 43) {
            begin = 40;
            finish = 43;
        } else if (age >= 44 && age <= 46) {
            begin = 44;
            finish = 46;
        } else if (age >= 47 && age <= 49) {
            begin = 47;
            finish = 49;
        } else if (age >= 50 && age <= 53) {
            begin = 50;
            finish = 53;
        } else if (age >= 54 && age <= 56) {
            begin = 54;
            finish = 56;
        } else if (age >= 57 && age <= 59) {
            begin = 57;
            finish = 59;
        } else if (age >= 60 && age <= 63) {
            begin = 60;
            finish = 63;
        } else if (age >= 64 && age <= 66) {
            begin = 64;
            finish = 66;
        } else if (age >= 67 && age <= 69) {
            begin = 67;
            finish = 69;
        } else if (age >= 70 && age <= 73) {
            begin = 70;
            finish = 73;
        } else if (age >= 74 && age <= 76) {
            begin = 74;
            finish = 76;
        } else if (age >= 77 && age <= 79) {
            begin = 77;
            finish = 79;
        } else if (age >= 80 && age <= 83) {
            begin = 80;
            finish = 83;
        } else if (age >= 84 && age <= 86) {
            begin = 84;
            finish = 86;
        } else if (age >= 87 && age <= 89) {
            begin = 87;
            finish = 89;
        } else if (age >= 90 && age <= 93) {
            begin = 90;
            finish = 93;
        } else if (age >= 94 && age <= 96) {
            begin = 94;
            finish = 96;
        } else if (age >= 97 && age <= 99) {
            begin = 97;
            finish = 99;
        } else {
            begin = 100;
            finish = 103;
        }

        return UserCompareResponse.builder()
                .data(
                        transactionHistoryDetailRepository.getComparison(
                                userId, interestId, begin, finish, year, month))
                .build();
    }

    @MethodInfo(
            name = "deleteUserInterestAndTransactionHistoryDetail",
            description = "사용자 관심사를 삭제합니다.")
    public void deleteUserInterestAndTransactionHistoryDetail(Long userId, Long interestId) {
        transactionHistoryDetailRepository.deleteByUserIdAndInterestId(userId, interestId);
        userInterestRepository.deleteByUserIdAndInterestId(userId, interestId);
    }

    public UserInterestService(
            InterestRepository interestRepository,
            UserInterestRepository userInterestRepository,
            TransactionHistoryRepository transactionHistoryRepository,
            TransactionHistoryDetailRepository transactionHistoryDetailRepository,
            SumAmountEventListener sumAmountEventListener) {
        this.interestRepository = interestRepository;
        this.userInterestRepository = userInterestRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistoryDetailRepository = transactionHistoryDetailRepository;
        this.sumAmountEventListener = sumAmountEventListener;
    }
}
