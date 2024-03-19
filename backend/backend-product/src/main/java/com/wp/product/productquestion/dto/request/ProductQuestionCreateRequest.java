package com.wp.product.productquestion.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "상품 문의 등록을 위한 요청 객체")
public class ProductQuestionCreateRequest {
    @NotNull
    @Schema(description = "상품 코드를 입력해주세요")
    private Long productId;

    @NotNull
    @Schema(description = "문의 내용을 입력해주세요")
    private String questionContent;
}
