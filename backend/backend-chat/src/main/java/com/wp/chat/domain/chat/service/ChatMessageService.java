package com.wp.chat.domain.chat.service;

import com.wp.chat.domain.chat.dto.request.ChatMessageRequest;

public interface ChatMessageService {
    void sendMessage(ChatMessageRequest chatMessageRequest);
}
