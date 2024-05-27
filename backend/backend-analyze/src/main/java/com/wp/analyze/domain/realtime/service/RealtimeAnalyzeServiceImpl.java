package com.wp.analyze.domain.realtime.service;

import com.wp.analyze.domain.realtime.utils.OpenviduConnection;
import com.wp.analyze.global.common.code.ErrorCode;
import com.wp.analyze.global.exception.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RealtimeAnalyzeServiceImpl implements RealtimeAnalyzeService{

    private final OpenviduConnection openviduConnection;
    private final StringRedisTemplate redisTemplate;

    @Override
    public List<String> getHotKeywords(String roomId) {
        try {
            List<ZSetOperations.TypedTuple<String>> hotKeywordList = Objects.requireNonNull(redisTemplate.opsForZSet().reverseRangeWithScores(roomId, 0, 5)).stream().toList();
            List<String> result = new ArrayList<>();
            for (ZSetOperations.TypedTuple<String> stringTypedTuple : hotKeywordList) {
                String keyword = Objects.requireNonNull(stringTypedTuple.getValue());
                result.add(keyword);
            }
            return result;
        }catch (Exception e){
            throw new BusinessExceptionHandler(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Long getConnectionNum(String roomId) {
        try {
            Long num = openviduConnection.getConnectionNum(roomId);
            redisTemplate.opsForHash().put(roomId, LocalDateTime.now().toString(), num.toString());
            return num;
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessExceptionHandler(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
