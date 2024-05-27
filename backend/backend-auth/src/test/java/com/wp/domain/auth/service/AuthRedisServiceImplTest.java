package com.wp.domain.auth.service;

import com.wp.domain.auth.entity.JwtToken;
import com.wp.domain.auth.repository.JwtTokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AuthRedisServiceImplTest {

    @Autowired
    private AuthRedisService authRedisService;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    String email = "test";
    String refreshToken = "test";

    @AfterEach
    void afterEach(){
        JwtToken jwtToken = JwtToken.builder().id(email).refreshToken(refreshToken).build();
        jwtTokenRepository.delete(jwtToken);
    }

    @Test
    @Rollback(value = false)
    void registRefreshToken() {
        boolean result_true = authRedisService.registRefreshToken(email, refreshToken);
        boolean result_false = authRedisService.registRefreshToken(email, refreshToken);
        assertThat(result_true).isEqualTo(true);
        assertThat(result_false).isEqualTo(false);
    }

    @Test
    @Rollback(value = false)
    void getRefreshToken() {
        authRedisService.registRefreshToken(email, refreshToken);
        JwtToken result = authRedisService.getRefreshToken(email);
        assertThat(result.getId()).isEqualTo(email);
        assertThat(result.getRefreshToken()).isEqualTo(refreshToken);
    }

    @Test
    @Rollback(value = false)
    void updateRefreshToken() {
        authRedisService.registRefreshToken(email, refreshToken);

        boolean result_true = authRedisService.updateRefreshToken(email, "test2");
        assertThat(result_true).isEqualTo(true);
        JwtToken result = authRedisService.getRefreshToken(email);
        assertThat(result.getRefreshToken()).isNotEqualTo(email);

        boolean result_false = authRedisService.updateRefreshToken("test2", this.refreshToken);
        assertThat(result_false).isEqualTo(false);
    }

    @Test
    @Rollback(value = false)
    void removeRefreshToken() {
        authRedisService.registRefreshToken(email, refreshToken);
        boolean result = authRedisService.removeRefreshToken(email);
        assertThat(result).isEqualTo(true);
    }
}