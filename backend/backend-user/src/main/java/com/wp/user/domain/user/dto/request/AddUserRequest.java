package com.wp.user.domain.user.dto.request;

import com.wp.user.domain.user.entity.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원가입을 위한 요청 객체")
public class AddUserRequest {
    @NotBlank(message = "회원의 로그인 ID를 입력해주세요.")
    @Size(max = 10, message = "로그인 ID는 최대 10이어야 합니다.")
    @Schema(description = "로그인 ID를 입력해주세요." , example = "ssafy")
    private String loginId;

    @NotBlank(message = "회원의 비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 16, message = "비밀번호는 8글자 이상 16글자 이하여야 합니다.")
    @Schema(description = "비밀번호를 입력해주세요." , example = "ssafy123!")
    private String password;

    @NotBlank(message = "회원의 이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    @Size(max = 100, message = "이메일은 크기는 최대 100이어야 합니다.")
    @Schema(description = "이메일을 입력해주세요." , example = "ssafy@gmail.com")
    private String email;

    @NotBlank(message = "회원 닉네임을 입력해 주세요.")
    @Size(max = 50, message = "닉네임은 크기는 최대 50이어야 합니다.")
    @Schema(description = "닉네임을 입력해주세요." , example = "김싸피")
    private String nickname;

    @NotNull(message = "회원의 성별을 입력해주세요.")
    @Schema(description = "성별을 입력해주세요." , example = "F")
    private Sex sex;

    @NotNull(message = "회원의 생년월일을 입력해주세요.")
    @Schema(description = "생년월일을 입력해주세요." , example = "2000-05-12")
    private LocalDate birthday;
}
