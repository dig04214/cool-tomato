package com.wp.product.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "상품 조회를 위한 요청 객체")
public class ProductSearchRequest {

    @Schema(defaultValue = "0",description = "페이지 수를 입력해주세요")
    private int page;

    @Min(10)
    @Schema(description = "페이지 크기를 입력해주세요")
    private int size;

    @Schema(description = "상품 카테고리 코드를 입력해주세요")
    private Long categoryId;

    @Schema(description = "판매자가 자신의 상품 목록 조회하는 거면 seller로",example = "seller")
    private String type;

    @Schema(description = "판매자 id")
    private Long sellerId;

}

