package com.wp.user.domain.seller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "판매자 전환 신청을 위한 요청 객체")
public class AddSellerInfoRequest {
    @NotEmpty
    @Schema(description = "사업자 번호를 입력해주세요." , example = "123-45-67890")
    private String businessNumber;

    @Schema(description = "판매자 설명을 입력해주세요." , example = "판매자 설명~~")
    private String businessContent;

    @Size(max = 100, message = "통신판매 신고번호는 최대 100이어야 합니다.")
    @Schema(description = "통신판매 신고번호를 입력해주세요." , example = "제2015-서울동작-0313호")
    private String mailOrderSalesNumber;

    @Schema(description = "주소를 입력해주세요." , example = "서울시특별시 동작구 노량진로 171 박문각빌딩 1층")
    private String businessAddress;

    @Size(max = 20, message = "대표 전화번호는 크기는 최대 50이어야 합니다.")
    @Schema(description = "대표 전화번호를 입력해주세요." , example = "02-3368-2413")
    private String phoneNumber;
}
