package com.wp.domain.auth.repository;

import com.wp.domain.auth.service.JwtTokenProviderService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtTokenRepositoryTest {
    // @Autowired
    // JwtTokenProviderService jwtTokenProvider;
    // String email = "";
    // String authorities = "user";
    // String AT;
    // String RT;
    //
    // @Value("${spring.jwt.secret}")
    // private String secretKey;
    // private Key signingKey;
    //
    // @Test
    // @Order(1)
    // public void afterPropertiesSet() throws Exception {
    // byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
    // signingKey = Keys.hmacShaKeyFor(secretKeyBytes);
    // }
    //
    //
    // @Test
    // @Order(2)
    // void createToken() {
    // List<String> user = jwtTokenProvider.createToken(email, authorities);
    // AT = user.get(0);
    // RT = user.get(1);
    //
    // Claims ATClaims = Jwts.parserBuilder()
    // .setSigningKey(signingKey)
    // .build()
    // .parseClaimsJws(AT)
    // .getBody();
    // assertThat(ATClaims.get("email")).isEqualTo(email);
    // assertThat(ATClaims.get("role")).isEqualTo(authorities);
    // }
    //
    // @Test
    // @Order(3)
    // void validateAccessToken() {
    // boolean result = jwtTokenProvider.validateAccessToken(AT);
    // assertThat(result).isEqualTo(true);
    // }
    //
    // @Test
    // @Order(4)
    // void validateRefreshToken() {
    // boolean result = jwtTokenProvider.validateRefreshToken(RT);
    // assertThat(result).isEqualTo(true);
    // }
    //
    // @Test
    // @Order(5)
    // void validateAccessTokenOnlyExpired() {
    // boolean result = jwtTokenProvider.validateAccessTokenOnlyExpired(AT);
    // assertThat(result).isEqualTo(true);
    // }
}