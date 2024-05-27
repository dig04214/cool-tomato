package com.wp.domain.auth.service;

import com.wp.domain.auth.dto.response.TokenResponseDto;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.InitializingBean;

import java.security.Key;
import java.util.List;
import java.util.Map;

public interface JwtTokenProviderService extends InitializingBean {

    public void afterPropertiesSet() throws Exception;
    public TokenResponseDto createToken(String userId, String auth);
    public boolean deleteRefreshToken(String userId);
    public boolean validateAccessToken(String accessToken);
    public boolean validateRefreshToken(String refreshToken, String userId);
    public String resolveToken(String token);
    public Claims getClaims(String token);
    public Map<String, String> getInfo(String token, List<String> infos);
    public String getUserId(String token);
    public String getAuth(String token);
}
