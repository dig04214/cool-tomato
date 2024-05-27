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
@Schema(description = "User Request DTO")
public class UserRequestDto {
    @NotBlank(message = "userId가 입력되지 않았습니다.")
    private String userId;
    @NotBlank(message = "auth가 입력되지 않았습니다.")
    private String auth;
}
