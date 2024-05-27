package com.wp.chat.global.common.service;

import com.wp.chat.global.common.request.AccessTokenRequest;
import com.wp.chat.global.common.request.ExtractionRequest;
import com.wp.chat.global.common.response.ExtractionResponse;
import com.wp.chat.global.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth", url = "MASKING_URL/v1/auth", configuration = FeignClientConfig.class)
public interface AuthClient {
    @PostMapping("/validationToken")
    String validateToken(@RequestBody AccessTokenRequest accessTokenRequest);

    @PostMapping("/extraction")
    ExtractionResponse extraction(@RequestBody ExtractionRequest extractionRequest);
}
