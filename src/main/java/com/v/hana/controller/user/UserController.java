package com.v.hana.controller.user;

import com.v.hana.auth.annotation.CurrentUser;
import com.v.hana.auth.dto.JwtToken;
import com.v.hana.auth.provider.JwtTokenProvider;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.response.PostSuccessResponse;
import com.v.hana.dto.user.UserJoinRequest;
import com.v.hana.dto.user.UserJwtTokenGetResponse;
import com.v.hana.dto.user.UserLoginRequest;
import com.v.hana.entity.user.User;
import com.v.hana.exception.user.InvalidUserAccessException;
import com.v.hana.repository.user.UserRepository;
import com.v.hana.service.user.UserService;
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
    public ResponseEntity<UserJwtTokenGetResponse> login(@RequestBody UserLoginRequest loginRequest) {
        Optional<User> user =
                userRepository.findByUsernameAndPw(
                        loginRequest.getUsername(), loginRequest.getPw());
        if (user.isEmpty()) throw new InvalidUserAccessException();
        JwtToken jwtToken = jwtTokenProvider.generateToken(user.get());
        UserJwtTokenGetResponse userJwtTokenGetResponse = UserJwtTokenGetResponse.builder()
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
        return ResponseEntity.ok(userJwtTokenGetResponse);
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

    @GetMapping("/users/username")
    public String getUserInfo(@CurrentUser UserDetails userDetails, @RequestParam String message) {
        return "User: " + userDetails.getUsername() + " Message: " + message;
    }
}
