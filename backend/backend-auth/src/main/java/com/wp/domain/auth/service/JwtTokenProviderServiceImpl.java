package com.wp.domain.auth.service;

import com.wp.domain.auth.dto.response.TokenResponseDto;
import com.wp.global.common.code.ErrorCode;
import com.wp.global.exception.BusinessExceptionHandler;
import io.jsonwebtoken.*;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
public class JwtTokenProviderServiceImpl implements JwtTokenProviderService {

    @Autowired
    private AuthRedisService authRedisService;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.token-validity-in-seconds}")
    private Long tokenValidityInMilliseconds;


    private String tmp;
    private Key signingKey;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    @Transactional
    public TokenResponseDto createToken(String userId, String auth){
        if(authRedisService.getRefreshToken(userId) != null){
            throw new BusinessExceptionHandler("이미 JWT 토큰을 발행했습니다.", ErrorCode.EXIST_TOKEN_ERROR);
        }

        Long now = System.currentTimeMillis();
        Long accessTokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000 * 2 * 24;
        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
                .setSubject("access-token")
                .claim(USER_ID, userId)
                .claim(AUTHORITIES_KEY, auth)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();

        Long refreshTokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000 * 2 * 24 * 7;
        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                .setSubject("refresh-token")
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();

        TokenResponseDto tokenResponseDto = TokenResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        authRedisService.registRefreshToken(userId, refreshToken);
        return tokenResponseDto;
    }

    public boolean deleteRefreshToken(String userId){
        if(authRedisService.removeRefreshToken(userId)) return true;
        else{
            throw new BusinessExceptionHandler("토큰을 삭제하는데 실패했습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean validateAccessToken(String accessToken) {
        try {
//            accessToken = resolveToken(accessToken);
            Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .build()
                    .parseClaimsJws(accessToken);
            return true;
        } catch(ExpiredJwtException e) {
            throw new BusinessExceptionHandler("만료된 JWT 토큰입니다.", ErrorCode.EXPIRED_TOKEN_ERROR);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new BusinessExceptionHandler("보안 규정에 어긋난 JWT 토큰입니다.", ErrorCode.SECURITY_TOKEN_ERROR);
        } catch (UnsupportedJwtException e) {
            throw new BusinessExceptionHandler("지원되지 않는 JWT 토큰입니다.", ErrorCode.UNSUPPORTED_TOKEN_ERROR);
        }
        catch (Exception e){
            throw new BusinessExceptionHandler("잘못된 JWT 토큰입니다.", ErrorCode.WRONG_TOKEN_ERROR);
        }
    }

    public boolean validateRefreshToken(String refreshToken, String userId){
        if(refreshToken.equals(authRedisService.getRefreshToken(userId).getRefreshToken())){
            try {
//                refreshToken = resolveToken(refreshToken);
                Jwts.parserBuilder()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                        .build()
                        .parseClaimsJws(refreshToken);
                return true;
            } catch(ExpiredJwtException e) {
                throw new BusinessExceptionHandler("만료된 JWT 토큰입니다", ErrorCode.EXPIRED_TOKEN_ERROR);
            } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
                throw new BusinessExceptionHandler("보안 정책에 위배되는 JWT 토큰입니다.", ErrorCode.SECURITY_TOKEN_ERROR);
            } catch (UnsupportedJwtException e) {
                throw new BusinessExceptionHandler("지원되지 않는 JWT 토큰입니다", ErrorCode.UNSUPPORTED_TOKEN_ERROR);
            } catch (Exception e){
                throw new BusinessExceptionHandler("잘못된 JWT 토큰입니다", ErrorCode.WRONG_TOKEN_ERROR);
            }
        }
        else{
            throw new BusinessExceptionHandler("사용자의 JWT 토큰이 아닙니다.", ErrorCode.NOT_MATCH_TOKEN_ERROR);
        }
    }

    @Override
    public String resolveToken(String token) {
        if (token != null && token.startsWith(BEARER_PREFIX)){
            return token.substring(7);
        }
        return null;
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) { // Access Token
            return e.getClaims();
        }
    }

    public Map<String, String> getInfo(String token, List<String> infos) {
        Claims claims = getClaims(token);
        Map<String, String> result = new HashMap<>();
        for (String info : infos) {
            result.put(info, claims.get(info).toString());
        }
        return result;
    }

    public String getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("userId").toString();
    }

    public String getAuth(String token) {
        Claims claims = getClaims(token);
        return claims.get("auth").toString();
    }
}
