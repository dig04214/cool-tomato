package com.wp.user.domain.alarm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "방송 시작 알림 전송을 위한 요청 객체")

public class BroadcastAlarmRequest {
    @NotNull(message = "판매자의 유저 ID를 입력해주세요.")
    @Schema(description = "방송하는 판매자 ID를 입력해주세요." , example = "1")
    Long sellerId;

    @Schema(description = "이동할 페이지 url을 입력해주세요." , example = "/follow")
    String alarmUrl;

    @Schema(description = "알림 메시지를 입력해주세요." , example = "00님 라이브 방송이 시작했습니다. 지금 바로 시청해보세요!")
    String content;
}