package com.wp.live.domain.broadcast.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartResponseDto {
    private String topicId;
    private String token;
}
