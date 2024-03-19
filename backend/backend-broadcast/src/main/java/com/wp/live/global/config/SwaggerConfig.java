package com.wp.live.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Access Token (Bearer)");

        Components components = new Components()
                .addSecuritySchemes("Access Token (Bearer)", accessTokenSecurityScheme);

        return new OpenAPI()
                .info(new Info()
                        .title("라이브 커머스 프로젝트 방송 API")
                        .description("방송 관련 기능을 제공합니다.")
                        .version("1.0.0"))
                .components(components)
                .addSecurityItem(securityRequirement);
    }
}