package com.v.hana.service.user;

import com.sun.jdi.request.DuplicateRequestException;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.constant.Gender;
import com.v.hana.entity.user.User;
import com.v.hana.repository.user.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@TypeInfo(name = "UserService", description = "유저 서비스 클래스")
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @MethodInfo(name = "join", description = "유저 서비스의 회원가입 메소드를 실행합니다.")
    public void join(String username, String name, String pw, String email, int gender) {
        // 회원 가입 정보 중복 확인
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateRequestException("이미 존재하는 이메일입니다.");
        } else if (userRepository.findByUsername(username).isPresent()) {
            throw new DuplicateRequestException("이미 존재하는 아이디입니다.");
        }
        // 회원 가입 진행
        userRepository.save(
                User.builder()
                        .birthday(LocalDate.from(LocalDateTime.now()))
                        .email(email)
                        .pw(pw)
                        .name(name)
                        .gender(getGenderFromInt(gender))
                        .username(username)
                        .build());
    }

    public Gender getGenderFromInt(int value) {
        Gender[] genders = Gender.values();
        if (value >= 0 && value < genders.length) {
            return genders[value];
        } else {
            throw new IllegalArgumentException("유효하지 않은 성별 값입니다.");
        }
    }
}
