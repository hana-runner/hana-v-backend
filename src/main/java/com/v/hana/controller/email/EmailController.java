package com.v.hana.controller.email;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.exception.BaseExceptionResponse;
import com.v.hana.common.response.GetSuccessResponse;
import com.v.hana.common.response.PostSuccessResponse;
import com.v.hana.exception.email.InvalidEmailAuthCodeException;
import com.v.hana.service.email.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "EmailController", description = "이메일 컨트롤러 클래스")
@RestController
@Tag(name = "Email", description = "이메일")
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class EmailController {
    private final MailService mailService;

    @Operation(
            summary = "이메일 인증 코드 전송",
            description = "이메일로 인증 코드를 전송합니다.",
            parameters = {@Parameter(name = "email", description = "이메일", required = true)},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이메일 인증 코드 전송 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                PostSuccessResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이메일 인증 코드 전송 실패",
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
    @PostMapping("/emails/authcode")
    public ResponseEntity<PostSuccessResponse> sendMessage(
            @RequestParam("email") @Valid String email) {
        mailService.sendCodeToEmail(email);

        return ResponseEntity.ok(PostSuccessResponse.builder().build());
    }

    @Operation(
            summary = "이메일 인증 코드 확인",
            description = "이메일로 전송된 인증 코드를 확인합니다.",
            parameters = {
                @Parameter(name = "email", description = "이메일", required = true),
                @Parameter(name = "code", description = "인증 코드", required = true)
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이메일 인증 코드 확인 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                GetSuccessResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이메일 인증 코드 확인 실패",
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
    @GetMapping("/emails/check/authcode")
    public ResponseEntity<GetSuccessResponse<Object>> verificationEmail(
            @RequestParam("email") @Valid String email, @RequestParam("code") String authCode) {
        boolean response = mailService.verifiedCode(email, authCode);
        if (!response) throw new InvalidEmailAuthCodeException();
        return ResponseEntity.ok(GetSuccessResponse.builder().build());
    }
}
