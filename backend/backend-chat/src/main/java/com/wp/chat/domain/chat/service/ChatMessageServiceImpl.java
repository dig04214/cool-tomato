package com.wp.chat.domain.chat.service;

import com.wp.chat.domain.chat.dto.request.ChatMessageRequest;
import com.wp.chat.domain.chat.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
    private final NewTopic topic;

    @Override
    public void sendMessage(ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = ChatMessage.builder().roomId(chatMessageRequest.getRoomId()).senderId(chatMessageRequest.getSenderId()).senderNickname(chatMessageRequest.getSenderNickname()).message(chatMessageRequest.getMessage()).build();
        kafkaTemplate.send(topic.name(), chatMessage);
    }
}
