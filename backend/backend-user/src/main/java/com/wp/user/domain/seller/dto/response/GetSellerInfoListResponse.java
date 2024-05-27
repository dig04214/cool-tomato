package com.wp.user.domain.seller.dto.response;

import com.wp.user.domain.seller.entity.SellerInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "판매자 전환 신청 목록 조회를 위한 응답 객체")
public class GetSellerInfoListResponse {
    int totalPage;
    long totalSize;
    List<GetSellerInfo> sellers;
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetSellerInfo {
        private Long sellerInfoId;
        private Long userId;
        private String loginId;
        private String nickname;
        private String profileImg;
        private boolean approvalStatus;
    }

    public static GetSellerInfoListResponse from(int totalPage, long totalSize, List<SellerInfo> sellerInfoList) {
        List<GetSellerInfo> sellers = new ArrayList<>();
        for (SellerInfo sellerInfo : sellerInfoList) {
            GetSellerInfo getSellerInfo = GetSellerInfo.builder()
                    .sellerInfoId(sellerInfo.getId())
                    .userId(sellerInfo.getUser().getId())
                    .loginId(sellerInfo.getUser().getLoginId())
                    .nickname(sellerInfo.getUser().getNickname())
                    .profileImg(sellerInfo.getUser().getProfileImg())
                    .approvalStatus(sellerInfo.isApprovalStatus()).build();
            sellers.add(getSellerInfo);
        }
        return GetSellerInfoListResponse.builder().totalPage(totalPage).totalSize(totalSize).sellers(sellers).build();
    }
}
