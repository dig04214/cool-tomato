package com.wp.user.domain.alarm.controller;

import com.wp.user.domain.alarm.dto.request.BroadcastAlarmRequest;
import com.wp.user.domain.alarm.dto.response.GetAlarmManageListResponse;
import com.wp.user.domain.alarm.service.AlarmManageService;
import com.wp.user.global.common.code.SuccessCode;
import com.wp.user.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/users/alarms")
@Tag(name = "알람 API", description = "알람 관리 용 API")
public class AlarmManageController {
    private final AlarmManageService alarmManageService;
    @GetMapping
    @Operation(summary = "알람 목록 조회", description = "사용자의 알림 목록을 조회합니다.")
    public ResponseEntity<SuccessResponse<?>> getAlarmManages(HttpServletRequest httpServletRequest) {
        GetAlarmManageListResponse getAlarmManageListResponse = alarmManageService.getAlarms(httpServletRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(getAlarmManageListResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping
    @Operation(summary = "알림 등록", description = "사용자의 알림을 등록합니다.")
    public ResponseEntity<SuccessResponse<?>> addBroadCastAlarm(HttpServletRequest httpServletRequest, @Valid @RequestBody BroadcastAlarmRequest broadcastAlarmRequest) {
        alarmManageService.sendBroadCastNotification(httpServletRequest, broadcastAlarmRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{alarm-id}")
    @Operation(summary = "알림 읽음", description = "사용자의 알림을 읽음 처리합니다.")
    public ResponseEntity<SuccessResponse<?>> modifyAlarm(HttpServletRequest httpServletRequest, @NotNull(message = "알림 ID를 입력해 주세요.") @PathVariable(name = "alarm-id") Long alarmId) {
        alarmManageService.setStatus(httpServletRequest, alarmId);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.UPDATE_SUCCESS.getStatus())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
