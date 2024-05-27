package com.wp.user.domain.seller.dto.response;

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
@Schema(description = "판매자 상세 정보 조회를 위한 응답 객체")
public class GetSellerResponse {
    Long userId;
    Long sellerInfoId;
    String loginId;
    String nickname;
    Sex sex;
    LocalDate birthday;
    String profileImg;
    Auth auth;
    Long followerCount;
    LocalDateTime joinDate;
}
