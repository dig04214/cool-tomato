package com.wp.chat.domain.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequest {
    @NotNull(message = "보내는 사람의 아이디를 입력해주세요.")
    private Long senderId;
    @NotBlank(message = "보내는 사람의 닉네임을 입력해주세요.")
    private String senderNickname;
    @NotBlank(message = "채팅방 아이디를 입력해주세요.")
    private String roomId;
    private String message;
}
