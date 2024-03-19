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
@Schema(description = "Access Token Request DTO")
public class AccessTokenRequestDto {
    @NotBlank(message = "Access Token을 입력해주세요.")
    private String accessToken;
}
