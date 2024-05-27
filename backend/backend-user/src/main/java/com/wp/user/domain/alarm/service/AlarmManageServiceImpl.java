package com.wp.user.domain.alarm.service;

import com.wp.user.domain.alarm.dto.request.BroadcastAlarmRequest;
import com.wp.user.domain.alarm.dto.response.GetAlarmManageListResponse;
import com.wp.user.domain.alarm.entity.AlarmManage;
import com.wp.user.domain.alarm.entity.AlarmToken;
import com.wp.user.domain.alarm.repository.AlarmManageRepository;
import com.wp.user.domain.alarm.repository.AlarmTokenRepository;
import com.wp.user.domain.follow.repository.FollowManageRepository;
import com.wp.user.domain.user.entity.User;
import com.wp.user.global.common.code.ErrorCode;
import com.wp.user.global.common.request.AccessTokenRequest;
import com.wp.user.global.common.request.ExtractionRequest;
import com.wp.user.global.common.service.AuthClient;
import com.wp.user.global.common.service.JwtService;
import com.wp.user.global.exception.BusinessExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmManageServiceImpl implements AlarmManageService {
    private final AlarmManageRepository alarmManageRepository;
    private final AlarmTokenRepository alarmTokenRepository;
    private final FollowManageRepository followManageRepository;
    private final JwtService jwtService;
    private final AuthClient authClient;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    // 알람 목록 조회
    @Override
    public GetAlarmManageListResponse getAlarms(HttpServletRequest httpServletRequest) {
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build()).getInfos();
        // 알람 목록
        List<AlarmManage> alarmManages = alarmManageRepository.findAllByUserId(Long.valueOf(infos.get("userId")));
        return GetAlarmManageListResponse.from(alarmManages);
    }

    // 방송 시작 알림
    @Override
    @Transactional
    public void sendBroadCastNotification(HttpServletRequest httpServletRequest, BroadcastAlarmRequest broadcastAlarmRequest) {
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 판매자를 팔로우한 사용자 id 찾기
        List<User> followerIds = followManageRepository.findAllFollowerByFollowingId(broadcastAlarmRequest.getSellerId());
        // 사용자 id로 토큰 찾아서 알람 보내기
        List<AlarmManage> alarmManages = new ArrayList<>();
        for (User user : followerIds) {
            Optional<AlarmToken> token = alarmTokenRepository.findById(user.getId());
            if(token.isPresent()) {
                try {
                    firebaseCloudMessageService.sendMessageTo(token.get().getToken(), "멋쟁이 토마토", broadcastAlarmRequest.getContent());
                } catch (Exception e) {
                    throw new BusinessExceptionHandler(ErrorCode.SEND_ALARM_ERROR);
                }
                AlarmManage alarmManage = AlarmManage.builder()
                        .user(user)
                        .alarmTitle("멋쟁이 토마토")
                        .alarmContent(broadcastAlarmRequest.getContent())
                        .alarmUrl(broadcastAlarmRequest.getAlarmUrl())
                        .status(false).build();
                alarmManages.add(alarmManage);
            }
        }
        alarmManageRepository.saveAll(alarmManages);
    }

    // 알람 읽음
    @Override
    @Transactional
    public void setStatus(HttpServletRequest httpServletRequest, Long alarmId) {
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 알람 읽음 처리
        AlarmManage alarmManage = alarmManageRepository.findById(alarmId).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_ALARM_ID));
        alarmManage.setStatus(true);
    }
}
