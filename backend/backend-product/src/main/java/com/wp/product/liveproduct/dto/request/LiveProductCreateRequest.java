package com.wp.product.liveproduct.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "라이브 상품 등록을 위한 요청 객체")
public class LiveProductCreateRequest {

    @NotNull
    @Schema(description = "상품 번호를 입력해주세요")
    private Long productId;

    @NotNull
    @Schema(description = "라이브 방송 번호를 입력해주세요")
    private Long liveId;

    @Schema(defaultValue = "0",description = "라이브 정액 가격을 입력해주세요")
    private int liveFlatPrice;

    @Schema(defaultValue = "0",description = "라이브 정률을 입력해주세요")
    private int liveRatePrice;

    @Schema(format = "date", description = "라이브 가격 적용 시작일을 입력해주세요")
    private String livePriceStartDate;

    @Schema(format = "date",description = "라이브 가격 적용 종료일을 입력해주세요")
    private String livePriceEndDate;

    @Schema(description = "메인 상품 여부를 입력해주세요")
    private Boolean mainProductSetting;

    @Schema(defaultValue = "0", description = "노출 순서를 입력해주세요")
    private int seq;
}
