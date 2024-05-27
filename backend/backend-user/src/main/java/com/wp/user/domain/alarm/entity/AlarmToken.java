package com.wp.user.domain.alarm.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@RedisHash(value = "alarm_token", timeToLive = 60*60*24*7)
public class AlarmToken {
    @Id
    private Long id; // userId
    private String token;
}
