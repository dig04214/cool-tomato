package com.wp.domain.auth.entity;

import com.wp.domain.auth.repository.JwtTokenRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtTokenRepositoryTest {

    @Autowired
    private JwtTokenRepository jwtTokenRepository;
    private JwtToken jwtToken;

    @Test
    @Order(1)
    @Rollback(value = false)
    void save() {
        jwtToken = JwtToken.builder().id("MASKING_EMAIL").refreshToken("test").build();
        JwtToken result = jwtTokenRepository.save(jwtToken);
        assertThat(jwtToken).isEqualTo(result);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    void find() {
        try {
            JwtToken result = jwtTokenRepository.findById(jwtToken.getId()).orElseThrow();
            assertThat(this.jwtToken).isEqualTo(result);
        } catch (NoSuchElementException e) {
            jwtToken = JwtToken.builder().id("MASKING_EMAIL").refreshToken("test").build();
            jwtTokenRepository.save(jwtToken);
            JwtToken result = jwtTokenRepository.findById(jwtToken.getId()).get();
            assertThat(jwtToken).isEqualTo(result);
        }
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    void update() {
        String refreshToken = jwtToken.getRefreshToken();
        jwtToken.setRefreshToken("test2");
        jwtTokenRepository.save(jwtToken);
        JwtToken result = jwtTokenRepository.findById(jwtToken.getId()).get();
        assertThat(result.getRefreshToken()).isNotEqualTo(refreshToken);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    void delete() {
        jwtTokenRepository.delete(jwtToken);
        assertThatThrownBy(() -> jwtTokenRepository.findById(jwtToken.getId()).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }
}