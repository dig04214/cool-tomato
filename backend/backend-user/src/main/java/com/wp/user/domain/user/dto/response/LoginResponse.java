package com.wp.user.domain.user.dto.response;

import com.wp.user.domain.user.entity.Auth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "로그인을 위한 응답 객체")
public class LoginResponse {
    Long userId;
    String nickname;
    String profileImg;
    Auth auth;
    String accessToken;
    String refreshToken;
}
