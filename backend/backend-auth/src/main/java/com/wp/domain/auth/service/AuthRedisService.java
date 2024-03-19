package com.wp.domain.auth.service;

import com.wp.domain.auth.entity.JwtToken;

public interface AuthRedisService {
    boolean registRefreshToken(String id, String refreshToken);
    JwtToken getRefreshToken(String id);
    boolean updateRefreshToken(String id, String refreshToken);
    boolean removeRefreshToken(String id);
}
