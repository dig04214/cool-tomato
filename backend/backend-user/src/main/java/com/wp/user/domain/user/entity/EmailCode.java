package com.wp.user.domain.user.entity;

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
@RedisHash(value = "email_code", timeToLive = 60*5L)
public class EmailCode {
    @Id
    private String id; // email
    private String code;
}