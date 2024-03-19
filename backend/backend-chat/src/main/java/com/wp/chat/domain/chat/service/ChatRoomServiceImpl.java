package com.wp.chat.domain.chat.service;

import com.wp.chat.global.common.code.ErrorCode;
import com.wp.chat.global.exception.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String HASH_KEY = "broadcast_info";
    @Override
    public Long getSellerId(String roomId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        long sellerId;
        try {
            sellerId = Long.parseLong(Objects.requireNonNull(hashOperations.get(HASH_KEY, roomId)));
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_START_BROADCAST);
        }
        return sellerId;
    }
}
