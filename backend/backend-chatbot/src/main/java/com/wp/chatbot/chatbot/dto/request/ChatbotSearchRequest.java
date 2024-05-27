package com.wp.chatbot.chatbot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "챗봇 질의응답 수정을 위한 요청 객체")
public class ChatbotSearchRequest {
    @Min(10)
    @Schema(description = "페이지 크기를 입력해주세요")
    private int size;

    @Schema(defaultValue = "0",description = "페이지 수를 입력해주세요")
    private int page;

    @NotNull
    @Schema(defaultValue = "0",description = "방송 id를 입력해주세요")
    private Long roomId;

    @Schema(defaultValue = "0",description = "판매자 id를 입력해주세요")
    private Long sellerId;
}
