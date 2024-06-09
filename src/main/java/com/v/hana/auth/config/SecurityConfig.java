package com.v.hana.auth.config;

import com.v.hana.auth.filter.JwtAuthenticationFilter;
import com.v.hana.auth.provider.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(
                        cors -> {
                            CorsConfigurationSource source =
                                    request -> {
                                        CorsConfiguration config = new CorsConfiguration();
                                        config.setAllowedOrigins(List.of("*"));
                                        config.setAllowedMethods(
                                                List.of("GET", "POST", "PUT", "DELETE"));
                                        config.setAllowedHeaders(List.of("*"));
                                        return config;
                                    };
                            cors.configurationSource(source);
                        })
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authz ->
                                authz.requestMatchers(
                                                "/swagger-ui/**",
                                                "/docs/**",
                                                "/v3/api-docs/**",
                                                "/v1/api/users/join",
                                                "/v1/api/users/login",
                                                "/v1/api/users/new_token",
                                                "/v1/api/users/check_dup",
                                                "/v1/api/users/update/pw",
                                                "/v1/api/users/find/username",
                                                "/v1/api/emails/authcode",
                                                "/v1/api/emails/check/authcode",
                                                "/v1/api/fcm/send")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
