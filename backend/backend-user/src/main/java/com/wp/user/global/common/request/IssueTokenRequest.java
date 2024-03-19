package com.wp.user.global.common.request;

import com.wp.user.domain.user.entity.Auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueTokenRequest {
    Long userId;
    Auth auth;
}
