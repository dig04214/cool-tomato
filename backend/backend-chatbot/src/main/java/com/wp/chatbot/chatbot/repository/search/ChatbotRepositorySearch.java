package com.wp.chatbot.chatbot.repository.search;

import com.wp.chatbot.chatbot.dto.request.ChatbotSearchRequest;
import com.wp.chatbot.chatbot.dto.response.ChatbotResponse;
import com.wp.chatbot.chatting.dto.MessageDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChatbotRepositorySearch {
    @Transactional
    Page<ChatbotResponse> search(ChatbotSearchRequest request);

    @Transactional
    String findChatbotByQuestion(MessageDto message,List<String> list);
}
