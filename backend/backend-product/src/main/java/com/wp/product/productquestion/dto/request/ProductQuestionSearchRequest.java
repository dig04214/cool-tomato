package com.wp.product.productquestion.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 문의 조회 페이징을 위한 요청 객체")
public class ProductQuestionSearchRequest {
    @Schema(defaultValue = "0",description = "페이지 수를 입력해주세요")
    private int page;

    @Min(10)
    @Schema(defaultValue = "10",description = "페이지 크기를 입력해주세요")
    private int size;

    @Schema(description = "상품 id")
    private Long productId;

    @Schema(description = "로그인 id")
    private Long loginId;
    
    @Schema(description = "판매자 id")
    private Long sellerId;

    @Schema(description = "구매자 id")
    private Long buyerId;
}
