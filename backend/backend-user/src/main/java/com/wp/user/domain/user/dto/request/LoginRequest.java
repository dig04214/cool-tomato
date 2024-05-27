package com.wp.user.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로그인을 위한 요청 객체")
public class LoginRequest {
    @NotBlank(message = "회원의 로그인 ID를 입력해주세요.")
    @Schema(description = "로그인 ID를 입력해주세요." , example = "ssafy")
    private String loginId;

    @NotBlank(message = "회원의 비밀번호를 입력해 주세요.")
    @Schema(description = "비밀번호를 입력해주세요." , example = "ssafy123!")
    private String password;

    @Schema(description = "알림을 받기 위한 FCM 토큰을 입력해주세요.")
    private String token;
}
