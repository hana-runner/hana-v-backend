package com.v.hana.controller.user;

import com.v.hana.auth.annotation.CurrentUser;
import com.v.hana.auth.dto.JwtToken;
import com.v.hana.auth.provider.JwtTokenProvider;
import com.v.hana.auth.util.user.SecurityUtil;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.response.DeleteSuccessResponse;
import com.v.hana.common.response.GetSuccessResponse;
import com.v.hana.common.response.PostSuccessResponse;
import com.v.hana.dto.user.*;
import com.v.hana.entity.user.User;
import com.v.hana.exception.user.InvalidUserAccessException;
import com.v.hana.exception.user.UserNameDuplicateException;
import com.v.hana.repository.user.UserRepository;
import com.v.hana.service.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "UserController", description = "유저 컨트롤러 클래스")
@RestController
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    @MethodInfo(name = "user join", description = "유저 컨트롤러의 회원 가입 메소드를 실행합니다.")
    @PostMapping("/users/join")
    public ResponseEntity<PostSuccessResponse> join(
            @Valid @RequestBody UserJoinRequest userJoinRequest) {
        userService.join(
                userJoinRequest.getUsername(),
                userJoinRequest.getName(),
                userJoinRequest.getPw(),
                userJoinRequest.getEmail(),
                userJoinRequest.getGender());
        return ResponseEntity.ok(PostSuccessResponse.builder().build());
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserJwtTokenGetResponse> login(
            @RequestBody UserLoginRequest loginRequest) {
        Optional<User> user =
                userRepository.findByUsernameAndPw(
                        loginRequest.getUsername(), loginRequest.getPw());
        if (user.isEmpty()) throw new InvalidUserAccessException();
        JwtToken jwtToken = jwtTokenProvider.generateToken(user.get());
        UserJwtTokenGetResponse userJwtTokenGetResponse =
                UserJwtTokenGetResponse.builder()
                        .accessToken(jwtToken.getAccessToken())
                        .refreshToken(jwtToken.getRefreshToken())
                        .build();
        return ResponseEntity.ok(userJwtTokenGetResponse);
    }

    @GetMapping("users/check_dup")
    public ResponseEntity<GetSuccessResponse> checkDupUsername(
            @RequestParam("username") String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserNameDuplicateException();
        }
        return ResponseEntity.ok(GetSuccessResponse.builder().build());
    }

    @GetMapping("users/new_token")
    public ResponseEntity<UserJwtTokenGetResponse> getNewAccessToken(
            @RequestParam("refresh_token") String refreshToken) {
        JwtToken jwtToken = jwtTokenProvider.getAccessToken(refreshToken);
        return ResponseEntity.ok(
                UserJwtTokenGetResponse.builder()
                        .accessToken(jwtToken.getAccessToken())
                        .refreshToken(jwtToken.getRefreshToken())
                        .build());
    }

    @GetMapping("/users/find/username")
    public ResponseEntity<GetSuccessResponse<Object>> findUsername(@RequestParam String email) {
        // 아이디 찾기
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new InvalidUserAccessException();
        return ResponseEntity.ok(GetSuccessResponse.builder().data(user.get().getName()).build());
    }

    @PostMapping("/users/update/pw")
    public ResponseEntity<GetSuccessResponse<Object>> findPw(
            @RequestBody UserChangePwRequest userChangePwRequest) {
        // 비밀번호 찾기(갱신)
        Optional<User> user = userRepository.findByEmail(userChangePwRequest.getEmail());
        if (user.isEmpty()) throw new InvalidUserAccessException();
        userRepository.updatePasswordByEmail(
                userChangePwRequest.getEmail(), userChangePwRequest.getPw());
        return ResponseEntity.ok(GetSuccessResponse.builder().data(user.get().getName()).build());
    }

    @DeleteMapping("/users/quit")
    @Transactional
    @CurrentUser
    public ResponseEntity<DeleteSuccessResponse> deleteCurrentUser() {
        User user = securityUtil.getCurrentUser();
        userRepository.deleteUserByEmail(user.getEmail());
        return ResponseEntity.ok(DeleteSuccessResponse.builder().build());
    }

    @GetMapping("users/info")
    @CurrentUser
    public ResponseEntity<UserGetResponse> getUserInfo() {
        User user = securityUtil.getCurrentUser();
        return ResponseEntity.ok(
                UserGetResponse.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .birthday(user.getBirthday())
                        .gender(user.getGender())
                        .build());
    }

    @PutMapping("users/update/email")
    @CurrentUser
    public ResponseEntity<PostSuccessResponse> updateUserEmail(
            @RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        User user = securityUtil.getCurrentUser();
        userRepository.updateEmailByUsername(
                user.getUsername(), updateUserInfoRequest.getEmail());
        return ResponseEntity.ok(PostSuccessResponse.builder().build());
    }
}
