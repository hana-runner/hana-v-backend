package com.v.hana.controller.email;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.response.GetSuccessResponse;
import com.v.hana.common.response.PostSuccessResponse;
import com.v.hana.exception.email.InvalidEmailAuthCodeException;
import com.v.hana.exception.user.UserNameDuplicateException;
import com.v.hana.service.email.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "EmailController", description = "이메일 컨트롤러 클래스")
@RestController
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class EmailController {
    private final MailService mailService;

    @PostMapping("/emails/authcode")
    public ResponseEntity<PostSuccessResponse> sendMessage(@RequestParam("email") @Valid String email) {
        mailService.sendCodeToEmail(email);

        return ResponseEntity.ok(PostSuccessResponse.builder().build());
    }

    @GetMapping("/emails/check/authcode")
    public ResponseEntity<GetSuccessResponse<Object>> verificationEmail(
            @RequestParam("email") @Valid String email, @RequestParam("code") String authCode){
        boolean response = mailService.verifiedCode(email, authCode);
        if (!response) throw new InvalidEmailAuthCodeException();
        return ResponseEntity.ok(GetSuccessResponse.builder().build());
    }
}
