package com.wp.product.liveproduct.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "라이브 상품 조회를 위한 요청 객체")
public class LiveProductSearchRequest {

    @Schema(defaultValue = "0",description = "페이지 수를 입력해주세요")
    private int page;

    @Min(10)
    @Schema(defaultValue = "10", description = "페이지 크기를 입력해주세요")
    private int size;

    @NotNull
    @Schema(description = "라이브 방송 아이디를 입력해주세요")
    private Long liveId;
}
