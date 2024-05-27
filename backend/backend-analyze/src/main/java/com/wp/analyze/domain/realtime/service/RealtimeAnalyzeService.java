package com.wp.analyze.domain.realtime.service;

import java.util.List;

public interface RealtimeAnalyzeService {
    public List<String> getHotKeywords(String roomId);
    public Long getConnectionNum(String roomId);
}
