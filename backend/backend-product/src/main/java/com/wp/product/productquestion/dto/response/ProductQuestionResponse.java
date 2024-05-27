package com.wp.product.productquestion.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "상품 문의 조회을 위한 응답 객체")
public class ProductQuestionResponse {
    private Long productQuestionBoardId;
    private Long writerId;
    private String writerNickname;
    private Long productId;
    private Long sellerId;
    private String imgSrc;
    private String productName;
    private String productContent;
    private String questionContent;
    private String answerContent;
    private LocalDateTime questionRegisterDate;
    private LocalDateTime answerRegisterDate;
}
