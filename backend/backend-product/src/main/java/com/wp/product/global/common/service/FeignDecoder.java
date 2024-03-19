package com.wp.product.global.common.service;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.wp.product.global.common.response.ErrorResponse;
import com.wp.product.global.common.response.SuccessResponse;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import java.io.IOException;
import java.lang.reflect.Type;

@Slf4j
public class FeignDecoder implements Decoder {
    private final Decoder decoder;

    public FeignDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        var returnType = TypeFactory.rawClass(type);
        var forClassWithGenerics =
                ResolvableType.forClassWithGenerics(SuccessResponse.class, returnType);

        try {
            return ((SuccessResponse<?>) decoder.decode(response,
                    forClassWithGenerics.getType())).getData();
        } catch (Exception e) {
            log.debug(e.getMessage());
            return (ErrorResponse) decoder.decode(response, forClassWithGenerics.getType());
        }
    }
}