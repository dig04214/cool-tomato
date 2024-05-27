package com.wp.analyze.domain.postmortem.service;

import com.wp.analyze.domain.postmortem.entity.BroadcastAnalyze;
import com.wp.analyze.domain.postmortem.repository.BroadcastAnalyzeRepository;
import com.wp.analyze.global.common.code.ErrorCode;
import com.wp.analyze.global.exception.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostmortemServiceImpl implements PostmortemService{

    private final BroadcastAnalyzeRepository broadcastAnalyzeRepository;
    @Override
    public String getPostmortemInfo(Long broadcastId) {
        try {
            BroadcastAnalyze queryResult = broadcastAnalyzeRepository.findByLiveBroadcastId(broadcastId).orElseThrow();
            return queryResult.getContent();
        }catch (NoSuchElementException e1){
            throw new BusinessExceptionHandler("해당 방송 분석 정보가 없습니다.", ErrorCode.NOT_FOUND_ERROR);
        }catch (Exception e2){
            throw new BusinessExceptionHandler(e2.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
