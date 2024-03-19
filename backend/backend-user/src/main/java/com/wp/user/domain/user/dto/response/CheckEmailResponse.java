package com.wp.user.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "이메일 인증을 위한 응답 객체")
public class CheckEmailResponse {
    String verifyCode;
}
