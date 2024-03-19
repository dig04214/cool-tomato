package com.wp.chatbot.chatting.controller;

import com.wp.chatbot.chatting.dto.MessageDto;
import com.wp.chatbot.chatting.service.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChattingController {

    private final SimpMessagingTemplate template;
    private final ChattingService chattingService;

    /**
     * 채팅 입장 시 입장 정보 전송
     * @param message
     */
    @MessageMapping(value = "/chat/enter")
    public void enter(MessageDto message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    /**
     * 채팅 보내는 경로
     * @param message
     */
    @MessageMapping(value = "/chat/message")
    public void message(MessageDto message){
        MessageDto answer = chattingService.searchByChatbot(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), answer);
    }
}
