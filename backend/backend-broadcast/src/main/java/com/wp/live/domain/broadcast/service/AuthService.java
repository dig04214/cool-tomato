package com.wp.live.domain.broadcast.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    public boolean validateToken(String token);
    public Long getUserId(String token);
    public String getAuth(String token);
    public String resolveAccessToken(HttpServletRequest httpServletRequest);
}
