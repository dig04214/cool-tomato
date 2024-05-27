package com.wp.product.global.config;

import com.wp.product.global.common.service.FeignDecoder;
import com.wp.product.global.common.service.FeignErrorDecoder;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.openfeign.support.SpringDecoder;

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