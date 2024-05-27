package com.wp.live.domain.broadcast.dto.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopRequestDto {
    private String accessToken;
    private Long liveBroadcastId;
}
