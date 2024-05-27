package com.wp.chatbot.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MessageDto {
    Long roomId;
    String message;
    Long writer;
}
