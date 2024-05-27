package com.wp.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Token Response DTO")
public class TokenResponseDto {
    @Schema(description = "Access Token")
    private String accessToken;
    @Schema(description = "Refresh Token")
    private String refreshToken;
}
