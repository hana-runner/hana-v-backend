package com.v.hana.service.interest;

import com.v.hana.command.interest.AddUserInterestCommand;
import com.v.hana.command.interest.GetUserInterestReportsCommand;
import com.v.hana.command.interest.GetUserInterestTransactionsCommand;
import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.command.interest.ModifyUserInterestCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.constant.Gender;
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
import java.util.List;
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
                        userInterestRepository.findByUserIdOrderById(command.getUserId()).stream()
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
            Long userId, Long interestId, int age, int year, int month, Gender gender) {

        int begin;
        int finish;

        switch (age / 3) {
            case 0 -> {
                begin = 0;
                finish = 3;
            }
            case 1 -> {
                begin = 4;
                finish = 6;
            }
            case 2 -> {
                begin = 7;
                finish = 9;
            }
            case 3 -> {
                begin = 10;
                finish = 13;
            }
            case 4 -> {
                begin = 14;
                finish = 16;
            }
            case 5 -> {
                begin = 17;
                finish = 19;
            }
            case 6 -> {
                begin = 20;
                finish = 23;
            }
            case 7 -> {
                begin = 24;
                finish = 26;
            }
            case 8 -> {
                begin = 27;
                finish = 29;
            }
            case 9 -> {
                begin = 30;
                finish = 33;
            }
            case 10 -> {
                begin = 34;
                finish = 36;
            }
            case 11 -> {
                begin = 37;
                finish = 39;
            }
            case 12 -> {
                begin = 40;
                finish = 43;
            }
            case 13 -> {
                begin = 44;
                finish = 46;
            }
            case 14 -> {
                begin = 47;
                finish = 49;
            }
            case 15 -> {
                begin = 50;
                finish = 53;
            }
            case 16 -> {
                begin = 54;
                finish = 56;
            }
            case 17 -> {
                begin = 57;
                finish = 59;
            }
            case 18 -> {
                begin = 60;
                finish = 63;
            }
            case 19 -> {
                begin = 64;
                finish = 66;
            }
            case 20 -> {
                begin = 67;
                finish = 69;
            }
            case 21 -> {
                begin = 70;
                finish = 73;
            }
            case 22 -> {
                begin = 74;
                finish = 76;
            }
            case 23 -> {
                begin = 77;
                finish = 79;
            }
            case 24 -> {
                begin = 80;
                finish = 83;
            }
            case 25 -> {
                begin = 84;
                finish = 86;
            }
            case 26 -> {
                begin = 87;
                finish = 89;
            }
            case 27 -> {
                begin = 90;
                finish = 93;
            }
            case 28 -> {
                begin = 94;
                finish = 96;
            }
            case 29 -> {
                begin = 97;
                finish = 99;
            }
            default -> {
                begin = 100;
                finish = 103;
            }
        }

//        ArrayList<UserComparisonDto> comparisonData = null;
        ArrayList<UserInterestConsumption> monthlyInterestConsumption = transactionHistoryDetailRepository.getMonthlyInterestConsumption(userId, interestId, year, month);
        List<Integer> categoryIds = monthlyInterestConsumption.stream()
                .map(UserInterestConsumption::getCategoryId)
                .map(Long::intValue)
                .distinct()
                .toList();
        ArrayList<UserInterestAvg> categoryAvg = transactionHistoryDetailRepository.getCategoryAvg(interestId, begin, finish, gender, year, month, categoryIds);


        ArrayList<UserComparisonDto> comparisonData = new ArrayList<>();
        for (UserInterestConsumption consumption : monthlyInterestConsumption) {
            Long average = categoryAvg.stream()
                    .filter(avg -> avg.getCategoryId().equals(consumption.getCategoryId()))
                    .map(UserInterestAvg::getAverage)
                    .findFirst()
                    .orElse(0L);

            Long difference = consumption.getExpense() - average;

            UserComparisonDto dto = UserComparisonDto.builder()
                    .interestId(consumption.getInterestId())
                    .categoryId(consumption.getCategoryId())
                    .interestTitle(consumption.getInterestTitle())
                    .categoryTitle(consumption.getCategoryTitle())
                    .expense(consumption.getExpense())
                    .average(average)
                    .difference(difference)
                    .gender(gender)
                    .build();

            comparisonData.add(dto);
        }

        return UserCompareResponse.builder().data(comparisonData).build();
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
