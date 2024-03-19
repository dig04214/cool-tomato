package com.wp.chatbot.chatting.service;

import com.wp.chatbot.chatting.dto.MessageDto;

public interface ChattingService {
    MessageDto searchByChatbot(MessageDto message);

    MessageDto searchByDB(MessageDto message);
}
