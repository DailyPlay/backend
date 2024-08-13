package com.example.haruplay.global.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                // 보안 스키마 이름 정의
                .name(jwt)
                // HTTP Bearer Token 방식 사용
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                // token 형식 : JWT
                .bearerFormat("JWT")
        );
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }
    private Info apiInfo() {
        return new Info()
                .title("HaruPlay API") // API의 제목
                .description("HaruPlay API Swagger UI") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }
}