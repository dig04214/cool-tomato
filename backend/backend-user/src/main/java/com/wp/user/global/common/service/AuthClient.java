package com.wp.user.global.common.service;

import com.wp.user.global.common.request.*;
import com.wp.user.global.common.response.ExtractionResponse;
import com.wp.user.global.common.response.IssueTokenResponse;
import com.wp.user.global.config.FeignClientConfig;
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

    @DeleteMapping("/id")
    String deleteTokenByUserId(@RequestBody DeleteTokenRequest deleteTokenRequest);

    @PostMapping("/validationToken")
    String validateToken(@RequestBody AccessTokenRequest accessTokenRequest);

    @PostMapping("/reissue")
    IssueTokenResponse reissueToken(@RequestBody TokenRequest tokenRequest);

    @PostMapping("/extraction")
    ExtractionResponse extraction(@RequestBody ExtractionRequest extractionRequest);
}
