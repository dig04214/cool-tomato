package com.wp.chatbot.chatbot.service;

import com.wp.chatbot.chatbot.dto.request.ChatbotCreateRequest;
import com.wp.chatbot.chatbot.dto.request.ChatbotSearchRequest;
import com.wp.chatbot.chatbot.dto.request.ChatbotUpdateRequest;
import java.util.Map;

public interface ChatbotService {
    Map<String, Object> search(ChatbotSearchRequest request);

    void save(ChatbotCreateRequest request,Long userId);

    void update(ChatbotUpdateRequest request,Long userId);

    void delete(Long chatbotId,Long userId);
}
