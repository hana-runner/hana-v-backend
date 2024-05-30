package com.v.hana.controller.user;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.dto.user.UserJoinRequest;
import com.v.hana.exception.user.UserEmailDuplicateException;
import com.v.hana.exception.user.UserNameDuplicateException;
import com.v.hana.service.user.UserService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TypeInfo(name = "UserController", description = "유저 컨트롤러 클래스")
@RestController
@RequestMapping("v1/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @MethodInfo(name = "user join", description = "유저 컨트롤러의 회원 가입 메소드를 실행합니다.")
    @PostMapping("/")
    public ResponseEntity<?> join(@Valid @RequestBody UserJoinRequest userJoinRequest) {
        try {
            userService.join(
                    userJoinRequest.getUsername(),
                    userJoinRequest.getName(),
                    userJoinRequest.getPw(),
                    userJoinRequest.getEmail(),
                    userJoinRequest.getGender());
            return ResponseEntity.ok(
                    Map.of(
                            "status",
                            200,
                            "success",
                            true,
                            "timestamp",
                            LocalDateTime.now()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                            "code",
                            "SUCCESS",
                            "message",
                            "User joined successfully",
                            "response",
                            1));
        } catch (UserEmailDuplicateException | UserNameDuplicateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
