package com.wp.live.domain.broadcast.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastAnalyzeInfo {
    private List<String> hotKeywords;
}
