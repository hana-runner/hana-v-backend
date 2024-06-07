package com.v.hana.controller.user;

import com.v.hana.auth.annotation.CurrentUser;
import com.v.hana.auth.dto.JwtToken;
import com.v.hana.auth.provider.JwtTokenProvider;
import com.v.hana.auth.util.user.SecurityUtil;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.exception.BaseExceptionResponse;
import com.v.hana.common.response.DeleteSuccessResponse;
import com.v.hana.common.response.GetSuccessResponse;
import com.v.hana.common.response.PostSuccessResponse;
import com.v.hana.dto.user.*;
import com.v.hana.entity.user.User;
import com.v.hana.exception.user.InvalidUserAccessException;
import com.v.hana.exception.user.UserNameDuplicateException;
import com.v.hana.repository.user.UserRepository;
import com.v.hana.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "UserController", description = "유저 컨트롤러 클래스")
@RestController
@Tag(name = "User", description = "사용자")
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    @MethodInfo(name = "user join", description = "유저 컨트롤러의 회원 가입 메소드를 실행합니다.")
    @PostMapping("/users/join")
    @Operation(
            summary = "사용자 회원 가입",
            description = "사용자 회원 가입을 진행합니다.",
            method = "POST",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "회원 가입 정보",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    UserJoinRequest.class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "회원 가입 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                PostSuccessResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "회원 가입 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
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
    @Operation(
            summary = "사용자 로그인",
            description = "사용자 로그인을 진행합니다.",
            method = "POST",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "로그인 정보",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    UserLoginRequest.class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "로그인 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                UserJwtTokenGetResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "로그인 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
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

    @Operation(
            summary = "사용자 중복 확인",
            description = "사용자 중복 확인을 진행합니다.",
            method = "GET",
            parameters = {
                @Parameter(
                        name = "username",
                        description = "사용자 이름",
                        required = true,
                        schema = @Schema(type = "string"))
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "사용자 중복이 없음",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                GetSuccessResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "사용자 중복이 있음",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    @GetMapping("users/check_dup")
    public ResponseEntity<GetSuccessResponse> checkDupUsername(
            @RequestParam("username") String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserNameDuplicateException();
        }
        return ResponseEntity.ok(GetSuccessResponse.builder().build());
    }

    @Operation(
            summary = "액세스 토큰 재발급",
            description = "액세스 토큰을 재발급합니다.",
            method = "GET",
            parameters = {
                @Parameter(
                        name = "refresh_token",
                        description = "리프레시 토큰",
                        required = true,
                        schema = @Schema(type = "string"))
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "액세스 토큰 발급 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                UserJwtTokenGetResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "액세스 토큰 발급 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
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

    @Operation(
            summary = "사용자 아이디 찾기",
            description = "사용자 아이디를 찾습니다.",
            method = "GET",
            parameters = {
                @Parameter(
                        name = "email",
                        description = "사용자 이메일",
                        required = true,
                        schema = @Schema(type = "string"))
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "아이디 찾기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                GetSuccessResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "아이디 찾기 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    @GetMapping("/users/find/username")
    public ResponseEntity<GetSuccessResponse<Object>> findUsername(@RequestParam String email) {
        // 아이디 찾기
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new InvalidUserAccessException();
        return ResponseEntity.ok(GetSuccessResponse.builder().data(user.get().getName()).build());
    }

    @Operation(
            summary = "사용자 비밀번호 찾기",
            description = "사용자 비밀번호를 찾습니다.",
            method = "POST",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "비밀번호 찾기 정보",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    UserChangePwRequest.class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "비밀번호 찾기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                GetSuccessResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "비밀번호 찾기 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
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

    @Operation(
            summary = "사용자 탈퇴",
            description = "사용자 탈퇴를 진행합니다.",
            method = "DELETE",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "탈퇴 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                DeleteSuccessResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "탈퇴 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    @DeleteMapping("/users/quit")
    @Transactional
    @CurrentUser
    public ResponseEntity<DeleteSuccessResponse> deleteCurrentUser() {
        User user = securityUtil.getCurrentUser();
        userRepository.deleteUserByEmail(user.getEmail());
        return ResponseEntity.ok(DeleteSuccessResponse.builder().build());
    }

    @Operation(
            summary = "사용자 정보 조회",
            description = "사용자 정보를 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "조회 성공",
                        content =
                                @Content(schema = @Schema(implementation = UserGetResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "조회 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    @GetMapping("users/info")
    @CurrentUser
    public ResponseEntity<UserGetResponse> getUserInfo() {
        User user = securityUtil.getCurrentUser();
        return ResponseEntity.ok(
                UserGetResponse.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .birthday(user.getBirthday())
                        .gender(user.getGender().ordinal())
                        .build());
    }

    @Operation(
            summary = "사용자 이메일 변경",
            description = "사용자 이메일을 변경합니다.",
            method = "PUT",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "이메일 변경 정보",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    UpdateUserInfoRequest.class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이메일 변경 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                PostSuccessResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이메일 변경 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    @PutMapping("users/update/email")
    @CurrentUser
    public ResponseEntity<PostSuccessResponse> updateUserEmail(
            @RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        User user = securityUtil.getCurrentUser();
        userRepository.updateEmailByUsername(user.getUsername(), updateUserInfoRequest.getEmail());
        return ResponseEntity.ok(PostSuccessResponse.builder().build());
    }
}
