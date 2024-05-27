package com.wp.user.domain.user.dto.response;

import com.wp.user.domain.user.entity.Auth;
import com.wp.user.domain.user.entity.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "개인 회원 정보 수정을 위한 응답 객체")
public class ModifyUserResponse {
    Long userId;
    String nickname;
    String profileImg;
    Auth auth;
    String accessToken;
    String refreshToken;
}
