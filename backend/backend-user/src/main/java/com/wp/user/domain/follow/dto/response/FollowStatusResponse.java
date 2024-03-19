package com.wp.user.domain.follow.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "팔로우 여부 조회를 위한 응답 객체")
public class FollowStatusResponse {
    boolean isFollow;
}
