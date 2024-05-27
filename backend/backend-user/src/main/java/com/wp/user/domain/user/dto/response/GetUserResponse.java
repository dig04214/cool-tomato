package com.wp.user.domain.user.dto.response;

import com.wp.user.domain.user.entity.Auth;
import com.wp.user.domain.user.entity.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "개인 회원 정보 조회를 위한 응답 객체")
public class GetUserResponse {
    Long id;
    String loginId;
    String nickname;
    Sex sex;
    LocalDate birthday;
    String profileImg;
    Auth auth;
    LocalDateTime joinDate;
    SellerInfo sellerInfo;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SellerInfo {
        Long sellerInfoId;
        String businessNumber;
        String businessContent;
        String mailOrderSalesNumber;
        String businessAddress;
        String phoneNumber;
        LocalDateTime registerDate;
        boolean approvalStatus;
    }
}
