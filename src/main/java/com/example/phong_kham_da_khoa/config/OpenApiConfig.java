package com.example.phong_kham_da_khoa.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI api() {
        final String bearerScheme = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Dental Clinic Platform API")
                        .description("Nền tảng đặt lịch phòng khám nha khoa")
                        .version("v1"))
                .components(new Components().addSecuritySchemes(
                        bearerScheme,
                        new SecurityScheme()
                                .name(bearerScheme)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .addSecurityItem(new SecurityRequirement().addList(bearerScheme));
    }
}
