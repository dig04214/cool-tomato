package com.wp.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Reissue Auth Token Request DTO")
public class ReissueAuthRequestDto {
    @NotBlank(message = "Access Token을 입력해주세요.")
    private String accessToken;
    @NotBlank(message = "Refresh Token을 입력해주세요.")
    private String refreshToken;
    @NotBlank(message = "Auth 입력해라잉.")
    private String auth;
}
