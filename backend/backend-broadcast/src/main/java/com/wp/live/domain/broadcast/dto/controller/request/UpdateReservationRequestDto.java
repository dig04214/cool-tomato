package com.wp.live.domain.broadcast.dto.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReservationRequestDto {
    private String accessToken;
    private Long broadcastId;
    private String broadcastTitle;
    private String content;
    private String script;
    private Boolean ttsSetting;
    private Boolean chatbotSetting;
    private LocalDateTime broadcastStartDate;
}
