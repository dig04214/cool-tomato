package com.wp.live.domain.broadcast.dto.controller.response;

import com.wp.live.domain.broadcast.dto.common.BroadcastInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCarouselResponseDto {
    List<BroadcastInfo> broadcastInfoList;
}
