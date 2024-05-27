package com.wp.product.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "상품 등록을 위한 요청 객체")
public class ProductCreateRequest {
    @NotNull
    @Schema(defaultValue = "1",description = "카테고리 코드를 입력해주세요")
    private Long categoryId;

    @Schema(description = "상품명을 입력해주세요")
    private String productName;

    @Schema(description = "상품 설명을 입력해주세요")
    private String productContent;

    @Schema(description = "상품 구매처를 입력해주세요")
    private String paymentLink;

    @Schema(defaultValue = "0",description = "상품 금액을 입력해주세요")
    private int price;

    @Schema(defaultValue = "0",description = "배송비를 입력해주세요")
    private int deliveryCharge;

    @Schema(defaultValue = "0",description = "상품 수량을 입력해주세요")
    private int quantity;
}
