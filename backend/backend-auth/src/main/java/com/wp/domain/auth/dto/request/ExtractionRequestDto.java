package com.wp.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Extraction Info DTO")
public class ExtractionRequestDto {
    @NotBlank(message = "Access Token을 입력해주세요.")
    private String accessToken;
    private List<String> infos;
}
