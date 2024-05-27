package com.wp.chatbot.chatbot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "상품 조회를 위한 요청 객체")
public class ChatbotCreateRequest {
    @NotNull
    @Schema(defaultValue = "0",description = "방송 id를 입력해주세요")
    private Long roomId;

    @NotNull
    @Schema(defaultValue = "질문",description = "질문 내용을 입력해주세요")
    private String question;

    @NotNull
    @Schema(defaultValue = "응답",description = "응답 내용을 입력해주세요")
    private String answer;
}
