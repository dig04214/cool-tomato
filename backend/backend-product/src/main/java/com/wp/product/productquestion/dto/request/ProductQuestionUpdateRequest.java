package com.wp.product.productquestion.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "상품 문의 답변 등록을 위한 요청 객체")
public class ProductQuestionUpdateRequest {
    @NotNull
    private Long productQuestionBoardId;

    @NotNull
    @Schema(description = "답변 내용을 입력해주세요")
    private String answerContent;
}
