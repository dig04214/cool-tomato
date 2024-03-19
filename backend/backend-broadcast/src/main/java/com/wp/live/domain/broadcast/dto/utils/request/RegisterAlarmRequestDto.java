package com.wp.live.domain.broadcast.dto.utils.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAlarmRequestDto {
    Long sellerId;
    String alarmUrl;
    String content;
}
