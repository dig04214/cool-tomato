package com.wp.product.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "상품 조회을 위한 응답 객체")
public class ProductFindResponse {
    private Long productId;
    private Long sellerId;
    private String sellerNickname;
    private String sellerProfile;
    private Long categoryId;
    private String categoryName;
    private String productName;
    private String productContent;
    private String paymentLink;
    private String imgSrc;
    private int price;
    private int deliveryCharge;
    private int quantity;
    private LocalDateTime registerDate;
}
