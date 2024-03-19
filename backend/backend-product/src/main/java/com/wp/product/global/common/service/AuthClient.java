package com.wp.product.global.common.service;

import com.wp.product.global.common.request.AccessTokenRequest;
import com.wp.product.global.common.request.ExtractionRequest;
import com.wp.product.global.common.request.IssueTokenRequest;
import com.wp.product.global.common.request.TokenRequest;
import com.wp.product.global.common.response.ExtractionResponse;
import com.wp.product.global.common.response.IssueTokenResponse;
import com.wp.product.global.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth", url = "MASKING_URL/v1/auth", configuration = FeignClientConfig.class)
public interface AuthClient {
    @PostMapping
    IssueTokenResponse issueToken(@RequestBody IssueTokenRequest issueTokenRequest);

    @DeleteMapping
    String deleteToken(@RequestBody AccessTokenRequest accessTokenRequest);

    @PostMapping("/validationToken")
    String validateToken(@RequestBody AccessTokenRequest accessTokenRequest);

    @PostMapping("/reissue")
    IssueTokenResponse reissueToken(@RequestBody TokenRequest tokenRequest);

    @PostMapping("/extraction")
    ExtractionResponse extraction(@RequestBody ExtractionRequest extractionRequest);
}
