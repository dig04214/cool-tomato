package com.wp.live.domain.broadcast.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastInfo {
    private Long liveBroadcastId;
    private String broadcastTitle;
    private String nickName;
    private Long viewCount;
    private Long sellerId;
    private Boolean broadcastStatus;
    private LocalDateTime startDate;
}
