package com.wp.product.liveproduct.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "라이브 상품 조회를 위한 응답 객체")
public class LiveBroadcastProductResponse {
    private Long liveProductId;
    //상품 내용
    private Long productId;
    private String imgSrc;
    private Long sellerId;
    private Long categoryId;
    private String categoryName;
    private String productName;
    private String productContent;
    private String paymentLink;
    private int price;
    private int deliveryCharge;
    private int quantity;
    
    //라이브 상품 내용
    private int liveFlatPrice;
    private int liveRatePrice;
    private LocalDateTime livePriceStartDate;
    private LocalDateTime livePriceEndDate;
    private Boolean mainProductSetting;
    private LocalDateTime registerDate;
    private int seq;

    //라이브 정보
    private Long liveBroadcastId;
    private String broadcastStatus;
}
