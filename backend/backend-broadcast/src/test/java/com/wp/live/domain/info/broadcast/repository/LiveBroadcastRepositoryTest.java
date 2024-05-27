package com.wp.live.domain.info.broadcast.repository;

import com.wp.live.domain.broadcast.entity.LiveBroadcast;
import com.wp.live.domain.broadcast.repository.LiveBroadcastRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LiveBroadcastRepositoryTest {
    @Autowired
    private LiveBroadcastRepository liveBroadcastRepository;
    private LiveBroadcast liveBroadcast;

    @Test
    @Order(1)
    @Rollback(value = false)
    void save(){
        liveBroadcast = LiveBroadcast.builder().broadcastTitle("test_title")
                .sellerId(1L)
                .content("test_content")
                .script("test_script")
                .ttsSetting(true)
                .chatbotSetting(true)
                .broadcastStartDate(LocalDateTime.now())
                .broadcastStatus(false)
                .build();
        LiveBroadcast save = liveBroadcastRepository.save(liveBroadcast);
        assertThat(save).isEqualTo(liveBroadcast);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    void find(){
        LiveBroadcast result = liveBroadcastRepository.findById(1L).orElseThrow();
        assertThat(result.getId()).isEqualTo(liveBroadcast.getId());
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    void update(){
        LiveBroadcast result1 = liveBroadcastRepository.findById(1L).orElseThrow();
        result1.setBroadcastStatus(true);
        LiveBroadcast result2 = liveBroadcastRepository.findById(1L).orElseThrow();
        assertThat(result2.getBroadcastStatus()).isEqualTo(true);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    void delete(){
        liveBroadcastRepository.deleteById(1L);
        assertThatThrownBy(() -> liveBroadcastRepository.findById(1L).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }
}