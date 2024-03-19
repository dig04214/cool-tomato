package com.wp.chatbot.global.common.request;

import  com.wp.chatbot.global.code.Auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueTokenRequest {
    Long userId;
    Auth auth;
}