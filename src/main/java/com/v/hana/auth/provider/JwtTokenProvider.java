package com.v.hana.auth.provider;

import com.v.hana.auth.dto.JwtToken;
import com.v.hana.entity.user.User;
import com.v.hana.repository.user.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private final UserRepository userRepository;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey, UserRepository userRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userRepository = userRepository;
    }

    // refresh access 인증 토큰 발급
    public JwtToken generateToken(User user) {

        Claims claims = Jwts.claims();
        claims.put("username", user.getUsername());

        long now = (new Date()).getTime();
        Date refreshTokenExpiresIn = new Date(now + 86400000); // 24시간 후 만료

        String refreshToken =
                Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(refreshTokenExpiresIn)
                        .signWith(SignatureAlgorithm.HS256, key)
                        .compact();

        return this.getAccessToken(refreshToken); // 새 엑세스 토큰 담아 JwtToken 발급
    }

    public JwtToken getAccessToken(String refreshToken) {
        // refresh token 으로부터 유저정보 추출해서 새 액세스 토큰 발급
        this.validateToken(refreshToken);
        User user = this.getUser(refreshToken);
        Claims claims = Jwts.claims();
        claims.put("username", user.getUsername());
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + 900000); // 15분 후 만료

        String newAccessToken =
                Jwts.builder()
                        .setClaims(claims) // claims를 먼저 설정
                        .setSubject(user.getEmail())
                        .setExpiration(accessTokenExpiresIn) // 그 다음 만료 시간을 설정
                        .signWith(SignatureAlgorithm.HS256, key)
                        .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 인증 토큰으로 유저 정보 불러오기
    public User getUser(String token) {
            this.validateToken(token);
            Claims claims =
                    Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            if (claims.get("username") == null) {
                throw new RuntimeException("유저 정보가 없는 토큰입니다.");
            }
            Optional<User> user = userRepository.findByUsername((String) claims.get("username"));
            if (user.isPresent()) {
                return user.get();
            }
            throw new RuntimeException("해당 아이디의 유저가 존재하지 않습니다.");
    }

    // 인증 토큰 유효성 검사
    public boolean validateToken(String accessToken) {
        try {

            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("만료된 토큰입니다.");
        }
    }
}
