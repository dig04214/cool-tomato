package com.wp.user.domain.alarm.dto.response;

import com.wp.user.domain.alarm.entity.AlarmManage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "알림 목록 조회를 위한 응답 객체")
public class GetAlarmManageListResponse {
    List<GetAlarmManage> alarm;
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetAlarmManage {
        private Long id;
        private String alarmTitle;
        private String alarmContent;
        private String alarmUrl;
        private boolean status;
        private LocalDateTime registerDate;
    }

    public static GetAlarmManageListResponse from(List<AlarmManage> alarmManageList) {
        List<GetAlarmManage> alarm = new ArrayList<>();
        for (AlarmManage alarmManage : alarmManageList) {
            GetAlarmManage getAlarmManage = GetAlarmManage.builder()
                    .id(alarmManage.getId())
                    .alarmTitle(alarmManage.getAlarmTitle())
                    .alarmContent(alarmManage.getAlarmContent())
                    .alarmUrl(alarmManage.getAlarmUrl())
                    .status(alarmManage.isStatus())
                    .registerDate(alarmManage.getRegisterDate()).build();
            alarm.add(getAlarmManage);
        }
        return GetAlarmManageListResponse.builder().alarm(alarm).build();
    }
}