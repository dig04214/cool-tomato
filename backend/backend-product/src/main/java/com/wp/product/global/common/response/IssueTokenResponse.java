package com.wp.product.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IssueTokenResponse {
    String accessToken;
    String refreshToken;
}