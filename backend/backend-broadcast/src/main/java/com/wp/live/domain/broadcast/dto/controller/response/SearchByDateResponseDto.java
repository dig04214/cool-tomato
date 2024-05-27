package com.wp.live.domain.broadcast.dto.controller.response;

import com.wp.live.domain.broadcast.dto.common.BroadcastInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchByDateResponseDto {
    private Long totalSize;
    private Integer totalPage;
    private List<BroadcastInfo> broadcastInfoList;
}
