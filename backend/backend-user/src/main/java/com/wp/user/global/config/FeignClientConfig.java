package com.wp.user.global.config;

import com.wp.user.global.common.service.FeignDecoder;
import com.wp.user.global.common.service.FeignErrorDecoder;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {
    @Bean
    public FeignErrorDecoder getFeignErrorDecoder(){
        return new FeignErrorDecoder();
    }
    @Bean
    public Decoder decoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new FeignDecoder(new SpringDecoder(messageConverters));
    }
}
