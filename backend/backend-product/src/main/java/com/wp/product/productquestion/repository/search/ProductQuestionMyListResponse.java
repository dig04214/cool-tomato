package com.wp.product.productquestion.repository.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "상품 문의 리스트 조회를 위한 응답 객체")
public class ProductQuestionMyListResponse {
    private Long productQuestionBoardId;
    private Long writerId;
    private String writerNickname;
    private Long productId;
    private String imgSrc;
    private String productName;
    private String productContent;
    private String questionContent;
    private String answerContent;
    private LocalDateTime questionRegisterDate;
    private LocalDateTime answerRegisterDate;
    private int answer;
}
