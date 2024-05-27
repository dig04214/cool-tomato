package com.wp.live.domain.broadcast.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.live.domain.broadcast.dto.common.BroadcastAnalyzeInfo;
import com.wp.live.domain.broadcast.dto.common.BroadcastInfo;
import com.wp.live.domain.broadcast.dto.controller.request.*;
import com.wp.live.domain.broadcast.dto.controller.response.GetBroadcastInfoResponseDto;
import com.wp.live.domain.broadcast.dto.controller.response.GetBroadcastListResponseDto;
import com.wp.live.domain.broadcast.entity.BroadcastAnalyze;
import com.wp.live.domain.broadcast.entity.LiveBroadcast;
import com.wp.live.domain.broadcast.entity.User;
import com.wp.live.domain.broadcast.repository.BroadcastAnalyzeRepository;
import com.wp.live.domain.broadcast.repository.LiveBroadcastRepository;
import com.wp.live.domain.broadcast.utils.MediateOpenviduConnection;
import com.wp.live.domain.broadcast.utils.UserConnection;
import com.wp.live.global.common.code.ErrorCode;
import com.wp.live.global.exception.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService{

    private final MediateOpenviduConnection mediateOpenviduConnection;
    private final LiveBroadcastRepository liveBroadcastRepository;
    private final StringRedisTemplate redisTemplate;
    private final BroadcastAnalyzeRepository broadcastAnalyzeRepository;
    private final UserConnection userConnection;
    private final String RANK="ranking";
    private final String VIEW="view";
    private final String HS="broadcast_info";

    @Override
    public Long reserveBroadcast(ReservationRequestDto reservation, Long sellerId) {
        User user = User.builder().id(sellerId).build();
        LiveBroadcast liveBroadcast = LiveBroadcast.builder()
                .user(user)
                .broadcastTitle(reservation.getBroadcastTitle())
                .content(reservation.getContent())
                .script(reservation.getScript())
                .ttsSetting(reservation.getTtsSetting())
                .chatbotSetting(reservation.getChatbotSetting())
                .broadcastStartDate(reservation.getBroadcastStartDate())
                .broadcastStatus(false)
                .viewCount(0L)
                .isDeleted(false)
                .build();
        LiveBroadcast save = liveBroadcastRepository.save(liveBroadcast);
        return save.getId();
    }

    @Override
    public Map<String, String> startBroadcast(StartRequestDto start, Long sellerId) {
        LiveBroadcast liveBroadcast;
        try {
            liveBroadcast = liveBroadcastRepository.findById(start.getLiveBroadcastId()).orElseThrow();
        }catch (NoSuchElementException e){
            throw new BusinessExceptionHandler("예약 내역이 없습니다.", ErrorCode.NOT_FOUND_ERROR);
        }

        if(!liveBroadcast.getUser().getId().equals(sellerId))throw new BusinessExceptionHandler("올바른 판매자가 아닙니다.", ErrorCode.FORBIDDEN_ERROR);

        try {
            String sessionId = mediateOpenviduConnection.getSessionId();
            String token = mediateOpenviduConnection.getToken(sessionId, "판매자");
            liveBroadcast.setBroadcastStatus(true);
            liveBroadcast.setSessionId(sessionId);
            liveBroadcast.setTopicId(sessionId);
            liveBroadcast.setBroadcastStartDate(LocalDateTime.now());
            liveBroadcastRepository.save(liveBroadcast);

            userConnection.registerAlarm(liveBroadcast.getUser().getId(), liveBroadcast.getTopicId(), liveBroadcast.getUser().getNickname(), start.getAccessToken());

            redisTemplate.opsForZSet().add(RANK, String.valueOf(start.getLiveBroadcastId()), 0);
            redisTemplate.opsForHash().put(VIEW, String.valueOf(start.getLiveBroadcastId()), "0");
            redisTemplate.expire(RANK, 3, TimeUnit.DAYS);
            redisTemplate.expire(VIEW, 3, TimeUnit.DAYS);

            redisTemplate.opsForHash().put(HS, String.valueOf(liveBroadcast.getId()), String.valueOf(sellerId));
            redisTemplate.expire(HS, 3, TimeUnit.DAYS);

            Map<String, String> result = new HashMap<>();
            result.put("topicId", sessionId);
            result.put("token", token);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessExceptionHandler("아이고 미안합니다. 김현종에게 문의해주세요~", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void stopBroadcast(StopRequestDto stop, Long sellerId) {
        LiveBroadcast liveBroadcast;
        try {
            liveBroadcast = liveBroadcastRepository.findById(stop.getLiveBroadcastId()).orElseThrow();
        }catch (NoSuchElementException e){
            throw new BusinessExceptionHandler("방송 내역이 없습니다.", ErrorCode.NOT_FOUND_ERROR);
        }

        if(!liveBroadcast.getUser().getId().equals(sellerId))throw new BusinessExceptionHandler("올바른 판매자가 아닙니다.", ErrorCode.FORBIDDEN_ERROR);

        try {
            Long viewCount = Long.parseLong((String)redisTemplate.opsForHash().get(VIEW, String.valueOf(stop.getLiveBroadcastId())));
            mediateOpenviduConnection.deleteSession(liveBroadcast.getSessionId());
            liveBroadcast.setBroadcastStatus(false);
            liveBroadcast.setIsDeleted(true);
            liveBroadcast.setViewCount(viewCount);
            liveBroadcast.setBroadcastEndDate(LocalDateTime.now());
            liveBroadcastRepository.save(liveBroadcast);

            List<ZSetOperations.TypedTuple<String>> hotKeywordList = Objects.requireNonNull(redisTemplate.opsForZSet().reverseRangeWithScores(String.valueOf(liveBroadcast.getId()), 0, 4)).stream().toList();
            List<String> hotKeywords = new ArrayList<>();
            for (ZSetOperations.TypedTuple<String> stringTypedTuple : hotKeywordList) {
                String keyword = Objects.requireNonNull(stringTypedTuple.getValue());
                hotKeywords.add(keyword);
            }

            BroadcastAnalyzeInfo analyzeInfo = BroadcastAnalyzeInfo.builder().hotKeywords(hotKeywords).build();
            ObjectMapper objectMapper = new ObjectMapper();
            String info = objectMapper.writeValueAsString(analyzeInfo);

            BroadcastAnalyze analyze = BroadcastAnalyze.builder().liveBroadcastId(liveBroadcast.getId()).content(info).build();
            broadcastAnalyzeRepository.save(analyze);
            redisTemplate.opsForZSet().remove(RANK, String.valueOf(stop.getLiveBroadcastId()));
            redisTemplate.opsForHash().delete(VIEW, Long.toString(stop.getLiveBroadcastId()));
        } catch (Exception e){
            throw new BusinessExceptionHandler("아이고 미안합니다. 김현종에게 문의해주세요~", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String participateBroadcast(ParticipationRequestDto participation) {
        try {
            LiveBroadcast liveBroadcast = liveBroadcastRepository.findById(participation.getLiveBroadcastId()).orElseThrow();
            redisTemplate.opsForZSet().incrementScore(RANK, String.valueOf(participation.getLiveBroadcastId()), 1);
            Long viewCount = Long.parseLong((String)redisTemplate.opsForHash().get(VIEW, String.valueOf(participation.getLiveBroadcastId()))) + 1;
            redisTemplate.opsForHash().put(VIEW, String.valueOf(participation.getLiveBroadcastId()), Long.toString(viewCount));
            return mediateOpenviduConnection.getToken(liveBroadcast.getSessionId(), "구매자");
        }catch (NoSuchElementException e) {
            throw new BusinessExceptionHandler("방송 내역이 없습니다.", ErrorCode.NOT_FOUND_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessExceptionHandler("아이고 미안합니다. 김현종에게 문의해주세요~", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GetBroadcastListResponseDto getBroadcastList(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
            Page<LiveBroadcast> queryResults = liveBroadcastRepository.findAllByBroadcastStatus(true, pageable);
            List<BroadcastInfo> infos = new ArrayList<>();
            for (LiveBroadcast queryResult : queryResults) {
                Long viewCount = Long.parseLong((String) redisTemplate.opsForHash().get(VIEW, String.valueOf(queryResult.getId())));
                BroadcastInfo info = BroadcastInfo.builder()
                        .liveBroadcastId(queryResult.getId())
                        .broadcastTitle(queryResult.getBroadcastTitle())
                        .sellerId(queryResult.getUser().getId())
                        .viewCount(viewCount)
                        .nickName(queryResult.getUser().getNickname())
                        .build();
                infos.add(info);
            }
            return GetBroadcastListResponseDto.builder().totalSize(queryResults.getTotalElements()).totalPage(queryResults.getTotalPages()).broadcastInfoList(infos).build();
        }catch (NoSuchElementException e1){
            throw new BusinessExceptionHandler("방송 내역이 없습니다.", ErrorCode.BAD_REQUEST_ERROR); //DB에 데이터가 없을 때 - JPA
        }catch (Exception e2){
            throw new BusinessExceptionHandler(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateBroadcastInfo(UpdateReservationRequestDto info) {
        try {
            LiveBroadcast liveBroadcast = liveBroadcastRepository.findById(info.getBroadcastId()).orElseThrow();
            liveBroadcast.setBroadcastTitle(info.getBroadcastTitle());
            liveBroadcast.setContent(info.getContent());
            liveBroadcast.setScript(info.getScript());
            liveBroadcast.setTtsSetting(info.getTtsSetting());
            liveBroadcast.setChatbotSetting(info.getChatbotSetting());
            liveBroadcast.setBroadcastStartDate(info.getBroadcastStartDate());
            liveBroadcastRepository.save(liveBroadcast);
        }catch (NoSuchElementException e1){
            throw new BusinessExceptionHandler("방송 내역이 없습니다.", ErrorCode.BAD_REQUEST_ERROR); //DB에 데이터가 없을 때 - JPA
        }catch (Exception e2){
            throw new BusinessExceptionHandler("아이고 미안합니다. 김현종에게 문의해주세요~", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GetBroadcastInfoResponseDto getBroadcastInfo(Long broadcastId) {
        try {
            LiveBroadcast liveBroadcast = liveBroadcastRepository.findById(broadcastId).orElseThrow();
            return GetBroadcastInfoResponseDto.builder()
                    .broadcastTitle(liveBroadcast.getBroadcastTitle())
                    .content(liveBroadcast.getContent())
                    .script(liveBroadcast.getScript())
                    .ttsSetting(liveBroadcast.getTtsSetting())
                    .chatbotSetting(liveBroadcast.getChatbotSetting())
                    .broadcastStartDate(liveBroadcast.getBroadcastStartDate())
                    .broadcastEndDate(liveBroadcast.getBroadcastEndDate())
                    .build();
        }catch (NoSuchElementException e1){
            throw new BusinessExceptionHandler("방송 내역이 없습니다.", ErrorCode.BAD_REQUEST_ERROR); //DB에 데이터가 없을 때 - JPA
        }catch (Exception e2){
            throw new BusinessExceptionHandler("아이고 미안합니다. 김현종에게 문의해주세요~", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GetBroadcastListResponseDto getReservationBroadcastListById(Long id, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
            Page<LiveBroadcast> queryResults = liveBroadcastRepository.findByUserIdAndIsDeleted(id, false, pageable);
            List<BroadcastInfo> infos = new ArrayList<>();
            for (LiveBroadcast queryResult : queryResults) {
                BroadcastInfo info = BroadcastInfo.builder()
                        .liveBroadcastId(queryResult.getId())
                        .broadcastTitle(queryResult.getBroadcastTitle())
                        .sellerId(queryResult.getUser().getId())
                        .nickName(queryResult.getUser().getNickname())
                        .build();
                infos.add(info);
            }
            return GetBroadcastListResponseDto.builder().totalSize(queryResults.getTotalElements()).totalPage(queryResults.getTotalPages()).broadcastInfoList(infos).build();
        }catch (NoSuchElementException e1){
            throw new BusinessExceptionHandler("방송 내역이 없습니다.", ErrorCode.BAD_REQUEST_ERROR); //DB에 데이터가 없을 때 - JPA
        }catch (Exception e2){
            e2.printStackTrace();
            throw new BusinessExceptionHandler(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GetBroadcastListResponseDto getStopBroadcastListById(Long id, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
            Page<LiveBroadcast> queryResults = liveBroadcastRepository.findByUserIdAndIsDeleted(id, true, pageable);
            List<BroadcastInfo> infos = new ArrayList<>();
            for (LiveBroadcast queryResult : queryResults) {
                BroadcastInfo info = BroadcastInfo.builder()
                        .liveBroadcastId(queryResult.getId())
                        .broadcastTitle(queryResult.getBroadcastTitle())
                        .sellerId(queryResult.getUser().getId())
                        .viewCount(queryResult.getViewCount())
                        .nickName(queryResult.getUser().getNickname())
                        .build();
                infos.add(info);
            }

            return GetBroadcastListResponseDto.builder().totalSize(queryResults.getTotalElements()).totalPage(queryResults.getTotalPages()).broadcastInfoList(infos).build();
        }catch (NoSuchElementException e1){
            throw new BusinessExceptionHandler("방송 내역이 없습니다.", ErrorCode.BAD_REQUEST_ERROR); //DB에 데이터가 없을 때 - JPA
        }catch (Exception e2){
            throw new BusinessExceptionHandler(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteBroadcast(Long broadcastId, Long sellerId) {
        LiveBroadcast liveBroadcast;
        try {
            liveBroadcast = liveBroadcastRepository.findById(broadcastId).orElseThrow();
            if(!liveBroadcast.getUser().getId().equals(sellerId))throw new BusinessExceptionHandler("올바른 판매자가 아닙니다.", ErrorCode.FORBIDDEN_ERROR);
        }catch (NoSuchElementException e1){
            throw new BusinessExceptionHandler("방송 내역이 없습니다.", ErrorCode.BAD_REQUEST_ERROR); //DB에 데이터가 없을 때 - JPA
        }

        try {
            liveBroadcast.setIsDeleted(true);
            liveBroadcastRepository.save(liveBroadcast);
        }catch (NoSuchElementException e1){
            throw new BusinessExceptionHandler("방송 내역이 없습니다.", ErrorCode.BAD_REQUEST_ERROR); //DB에 데이터가 없을 때 - JPA
        }catch (Exception e2){
            throw new BusinessExceptionHandler(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
