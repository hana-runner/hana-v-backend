package com.v.hana.service.interest;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.UserInterestDto;
import com.v.hana.dto.UserInterestResponse;
import com.v.hana.entity.interest.UserInterest;
import com.v.hana.entity.interest.Interest;
import com.v.hana.repository.interest.UserInterestRepository;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.v.hana.repository.interest.InterestRepository;
import com.v.hana.repository.user.UserRepository;
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

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private UserInterestRepository userInterestRepository;

    public UserInterestResponse getUserInterests(Long userId) {
        ArrayList<UserInterest> userInterests = userInterestRepository.findByUserId(userId);

        return UserInterestResponse.builder().
                data(
                userInterestRepository.findByUserId(userId).stream()
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
                        .collect(Collectors.toCollection(ArrayList::new))
        )
                .build();
    }
}
