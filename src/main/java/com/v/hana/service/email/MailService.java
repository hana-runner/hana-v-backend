package com.v.hana.service.email;

import com.v.hana.service.RedisService;
import jakarta.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {
    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    private final JavaMailSender emailSender;
    private final RedisService redisService;

    public void sendEmail(String toEmail, String title, String text) {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            logger.error(
                    "MailService.sendEmail exception occur toEmail: {}, " + "title: {}, text: {}",
                    toEmail,
                    title,
                    text);
        }
    }

    private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }

    public void sendCodeToEmail(String toEmail) {
        String title = "HanaV 이메일 인증 번호";
        String authCode = this.createCode();
        this.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.set(
                AUTH_CODE_PREFIX + toEmail,
                authCode,
                Duration.ofMillis(this.authCodeExpirationMillis));
    }

    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("MemberService.createCode() exception occur");
            return "";
        }
    }

    public boolean verifiedCode(String email, String authCode) {
        if (redisService.checkExistsValue(AUTH_CODE_PREFIX + email)) {
            String redisAuthCode = (String) redisService.get(AUTH_CODE_PREFIX + email); // value
            return redisAuthCode.equals(authCode);
        }

        return false;
    }
}
