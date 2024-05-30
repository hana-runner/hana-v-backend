package com.v.hana.service;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.UserInterestResponse;
import com.v.hana.entity.Interest;
import com.v.hana.entity.UserInterest;
import com.v.hana.repository.InterestRepository;
import com.v.hana.repository.UserInterestRepository;
import com.v.hana.repository.UserRepository;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@TypeInfo(name = "UserInterestService", description = "예시 서비스 클래스")
@Service
public class UserInterestService {

    private final UserRepository userRepository;

    @Autowired
    UserInterestService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired private InterestRepository interestRepository;

    @Autowired private UserInterestRepository userInterestRepository;

    public ArrayList<UserInterestResponse> getUserInterests(Long userId) {
        ArrayList<UserInterest> userInterests = userInterestRepository.findByUserId(userId);

        return userInterests.stream()
                .map(
                        userInterest -> {
                            Interest interest =
                                    interestRepository
                                            .findById(userInterest.getInterestId())
                                            .orElseThrow(
                                                    () ->
                                                            new RuntimeException(
                                                                    "Interest not found"));
                            return UserInterestResponse.builder()
                                    .title(interest.getTitle())
                                    .subtitle(userInterest.getSubtitle())
                                    .imageUrl(userInterest.getImageUrl())
                                    .color(interest.getColor())
                                    .build();
                        })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
