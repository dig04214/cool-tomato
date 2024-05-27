package com.wp.user.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "로그인 아이디 중복 확인을 위한 응답 객체")
public class DuplicateLoginIdResponse {
    boolean isDuplicate;
}
