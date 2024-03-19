package com.wp.product.global.common.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    private static final String BEARER_PREFIX = "Bearer ";

    // Request의 Header에서 AccessToken 값을 가져옵니다. "Authorization" : "Bearer token"
    public String resolveAccessToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
    // Request의 Header에서 RefreshToken 값을 가져옵니다. "Refresh Token" : "token"
    public String resolveRefreshToken(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(REFRESH_TOKEN_HEADER);
    }
}