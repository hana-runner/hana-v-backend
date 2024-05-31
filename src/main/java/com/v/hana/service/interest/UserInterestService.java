package com.v.hana.service.interest;

import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.UserInterestDto;
import com.v.hana.dto.UserInterestResponse;
import com.v.hana.entity.interest.Interest;
import com.v.hana.entity.interest.UserInterest;
import com.v.hana.repository.interest.InterestRepository;
import com.v.hana.repository.interest.UserInterestRepository;
import com.v.hana.usecase.interest.UserInterestUseCase;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@TypeInfo(name = "UserInterestService", description = "예시 서비스 클래스")
@Service
public class UserInterestService implements UserInterestUseCase {
    private final InterestRepository interestRepository;

    private final UserInterestRepository userInterestRepository;

    @MethodInfo(name = "getUserInterests", description = "유저의 관심사를 조회합니다.")
    @Override
    public UserInterestResponse getUserInterests(GetUserInterestsCommand command) {
        ArrayList<UserInterest> userInterests =
                userInterestRepository.findByUserId(command.getUserId());

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
                                                    .title(interest.getTitle())
                                                    .subtitle(userInterest.getSubtitle())
                                                    .imageUrl(userInterest.getImageUrl())
                                                    .color(interest.getColor())
                                                    .build();
                                        })
                                .collect(Collectors.toCollection(ArrayList::new)))
                .build();
    }

    public UserInterestService(
            InterestRepository interestRepository, UserInterestRepository userInterestRepository) {
        this.interestRepository = interestRepository;
        this.userInterestRepository = userInterestRepository;
    }
}
