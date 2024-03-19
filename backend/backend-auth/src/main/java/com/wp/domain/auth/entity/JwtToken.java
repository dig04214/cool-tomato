package com.wp.domain.auth.entity;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@RedisHash(value = "jwt_token", timeToLive = (60L * 60L * 24L * 7L))
public class JwtToken {
    @Id
    private String id; //user_id
    private String refreshToken;
}
