package com.wp.chatbot.global.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.chatbot.global.common.code.ErrorCode;
import com.wp.chatbot.global.common.response.ErrorResponse;
import com.wp.chatbot.global.exception.BusinessExceptionHandler;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import lombok.SneakyThrows;

public class FeignErrorDecoder implements ErrorDecoder {
    private final StringDecoder stringDecoder = new StringDecoder();
    @Override
    @SneakyThrows
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new BusinessExceptionHandler(ErrorCode.BAD_REQUEST_ERROR);
            case 401:
                String message = stringDecoder.decode(response, String.class).toString();
                ObjectMapper objectMapper = new ObjectMapper();
                ErrorResponse errorResponse = objectMapper.readValue(message, ErrorResponse.class);
                switch(errorResponse.getDivisionCode()) {
                    case "G013" :
                        throw new BusinessExceptionHandler("만료된 JWT 토큰입니다.", ErrorCode.EXPIRED_TOKEN_ERROR);
                    case "G014" :
                        throw new BusinessExceptionHandler("보안 규정에 어긋난 JWT 토큰입니다.", ErrorCode.SECURITY_TOKEN_ERROR);
                    case "G015" :
                        throw new BusinessExceptionHandler("지원되지 않는 JWT 토큰입니다.", ErrorCode.UNSUPPORTED_TOKEN_ERROR);
                    default :
                        throw new BusinessExceptionHandler("잘못된 JWT 토큰입니다.", ErrorCode.WRONG_TOKEN_ERROR);
                }
            case 403:
                return new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
            default:
                return new Exception(response.reason());
        }
    }
}