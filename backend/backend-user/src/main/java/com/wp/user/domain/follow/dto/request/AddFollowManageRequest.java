package com.wp.user.domain.follow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "팔로우 등록을 위한 요청 객체")
public class AddFollowManageRequest {
    @Schema(description = "팔로우할 판매자 ID를 입력해주세요." , example = "1")
    private Long sellerId;

    @Schema(description = "알람 설정 여부를 입력해주세요." , example = "false")
    private boolean alarmSetting;
}
