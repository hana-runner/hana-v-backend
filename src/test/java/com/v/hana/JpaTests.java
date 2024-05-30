package com.v.hana;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.v.hana.constant.Gender;
import com.v.hana.entity.User;
import com.v.hana.repository.user.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class JpaTests {
    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("유저 생성 테스트")
    void insert_user() {
        // 유저 등록
        User user =
                userRepository.save(
                        User.builder()
                                .birthday(LocalDate.from(LocalDateTime.now()))
                                .email("borieya0619@gmail.com")
                                .pw("123")
                                .name("박효리")
                                .gender(Gender.FEMALE)
                                .username("박효리")
                                .build());

        Optional<User> optionalUser = userRepository.findById(user.getId());
        assertEquals(user.getUsername(), optionalUser.get().getUsername());
    }
}
