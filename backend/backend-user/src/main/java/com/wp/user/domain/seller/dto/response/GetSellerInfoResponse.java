package com.wp.user.domain.seller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "판매자 전환 신청 상세 조회를 위한 응답 객체")
public class GetSellerInfoResponse {
    Long sellerInfoId;
    Long userId;
    String businessNumber;
    String businessContent;
    String mailOrderSalesNumber;
    String businessAddress;
    String phoneNumber;
    LocalDateTime registerDate;
    boolean approvalStatus;
}
