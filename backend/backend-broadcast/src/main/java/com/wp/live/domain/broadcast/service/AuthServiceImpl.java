package com.wp.live.domain.broadcast.service;

import com.wp.live.domain.broadcast.utils.AuthConnection;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    AuthConnection authConnection;

    @Override
    public boolean validateToken(String token) {
        return authConnection.validationToken(token);
    }

    @Override
    public Long getUserId(String token) {
        List<String> info = new ArrayList<>();
        info.add("userId");
        Map<String, Map<String, String>> result = authConnection.getInfo(token, info);
        return Long.parseLong(result.get("infos").get("userId"));
    }

    @Override
    public String getAuth(String token) {
        List<String> info = new ArrayList<>();
        info.add("auth");
        Map<String, Map<String, String>> result = authConnection.getInfo(token, info);
        return result.get("infos").get("auth");
    }

    public String resolveAccessToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
