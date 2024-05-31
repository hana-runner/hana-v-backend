package com.v.hana.controller.email;

import com.v.hana.common.annotation.TypeInfo;
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

    @PostMapping("/emails/verification-requests")
    public ResponseEntity sendMessage(@RequestParam("email") @Valid String email) {
        mailService.sendCodeToEmail(email);

        return ResponseEntity.status(HttpStatus.OK).body("이메일 전송 완료");
    }

    @GetMapping("/emails/verifications")
    public ResponseEntity verificationEmail(
            @RequestParam("email") @Valid String email, @RequestParam("code") String authCode) {
        boolean response = mailService.verifiedCode(email, authCode);
        if (!response) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 번호가 틀렸습니다.");
        return ResponseEntity.status(HttpStatus.OK).body("인증 성공");
    }
}
