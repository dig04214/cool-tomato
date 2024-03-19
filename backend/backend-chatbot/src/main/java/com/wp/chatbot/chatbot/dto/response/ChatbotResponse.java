package com.wp.chatbot.chatbot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "챗봇 문의 조회를 위한 응답 객체")
public class ChatbotResponse {
    private Long chatbotId;
    private Long roomId;
    private String question;
    private String answer;
    private LocalDateTime registerDate;
}
