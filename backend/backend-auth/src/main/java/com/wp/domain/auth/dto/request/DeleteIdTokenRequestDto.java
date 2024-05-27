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
@Schema(description = "Delete Id Token Request DTO")
public class DeleteIdTokenRequestDto {
    @NotBlank(message = "userId가 입력되지 않았습니다.")
    private String userId;
}
