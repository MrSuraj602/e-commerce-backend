package com.MrSuraj.eco.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "E-Commerce API",
                version = "1.0.0",
                description = "REST API for customer authentication, product catalog browsing, carts, orders, payments, product ratings and reviews, user profiles, and administrative catalog and order operations. Authenticate with /auth/signup or /auth/signin, then authorize protected requests with the returned JWT."
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Authenticate through /auth/signup or /auth/signin and paste the returned JWT token here. Swagger UI sends it as Authorization: Bearer <token>."
)
public class OpenApiConfig {
}