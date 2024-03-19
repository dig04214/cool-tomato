package com.wp.chat.domain.block.dto.response;

import com.wp.chat.domain.block.entity.BlockManage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "차단 목록 조회를 위한 응답 객체")
public class GetBlockManageListResponse {
    List<GetBlockManage> block;
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetBlockManage {
        private Long id;
        private Long userId;
        private String loginId;
        private String nickname;
        private String profileImg;
    }

    public static GetBlockManageListResponse from(List<BlockManage> blockManageList) {
        List<GetBlockManage> block = new ArrayList<>();
        for (BlockManage blockManage : blockManageList) {
            GetBlockManage getBlockManage = GetBlockManage.builder()
                    .id(blockManage.getId())
                    .userId(blockManage.getBlocked().getId())
                    .loginId(blockManage.getBlocked().getLoginId())
                    .nickname(blockManage.getBlocked().getNickname())
                    .profileImg(blockManage.getBlocked().getProfileImg()).build();
            block.add(getBlockManage);
        }
        return GetBlockManageListResponse.builder().block(block).build();
    }
}
