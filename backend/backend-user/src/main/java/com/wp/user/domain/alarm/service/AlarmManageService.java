package com.wp.user.domain.alarm.service;

import com.wp.user.domain.alarm.dto.request.BroadcastAlarmRequest;
import com.wp.user.domain.alarm.dto.response.GetAlarmManageListResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AlarmManageService {
    GetAlarmManageListResponse getAlarms(HttpServletRequest httpServletRequest);

    void sendBroadCastNotification(HttpServletRequest httpServletRequest, BroadcastAlarmRequest broadcastAlarmRequest);
    void setStatus(HttpServletRequest httpServletRequest, Long alarmId);
}
