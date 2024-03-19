package com.wp.user.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "이메일 인증을 위한 요청 객체")
public class CheckEmailRequest {
    @NotBlank(message = "회원의 이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    @Size(max = 100, message = "이메일은 크기는 최대 100이어야 합니다.")
    @Schema(description = "이메일을 입력해주세요.", example = "ssafy@gmail.com")
    private String email;
}
