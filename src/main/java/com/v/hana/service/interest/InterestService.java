package com.v.hana.service.interest;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.interest.InterestDto;
import com.v.hana.dto.interest.InterestsResponse;
import com.v.hana.entity.interest.Interest;
import com.v.hana.repository.interest.InterestRepository;
import com.v.hana.usecase.interest.InterestUseCase;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@TypeInfo(name = "InterestService", description = "관심사 서비스")
@Service
public class InterestService implements InterestUseCase {

    private final InterestRepository interestRepository;

    @MethodInfo(name = "getUserInterests", description = "관심사 목록 조회")
    @Override
    public InterestsResponse getInterests() {
        return InterestsResponse.builder()
                .data(
                        interestRepository.findAll().stream()
                                .map(
                                        interest ->
                                                InterestDto.builder()
                                                        .interestId(interest.getId())
                                                        .title(interest.getTitle())
                                                        .description(interest.getDescription())
                                                        .color(interest.getColor())
                                                        .build())
                                .collect(Collectors.toCollection(ArrayList::new)))
                .build();
    }

    @MethodInfo(name = "getInterestsById", description = "관심사 ID로 관심사 목록 조회")
    @Override
    public ArrayList<Interest> getInterestsById(ArrayList<Long> ids) {
        return interestRepository.findAllById(ids);
    }

    public InterestService(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }
}
