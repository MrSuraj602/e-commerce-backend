package com.MrSuraj.eco.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

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
        @Bean
        OpenApiCustomizer standardErrorResponses() {
                return openApi -> {
                        addErrorSchema(openApi);
                        openApi.getPaths().forEach((path, pathItem) -> pathItem.readOperations().forEach(operation -> {
                                addErrorResponse(operation, "400", "Request data is invalid or incomplete");
                                addErrorResponse(operation, "500", "Unexpected server-side failure");
                                if (operation.getSecurity() != null && !operation.getSecurity().isEmpty()) {
                                        addErrorResponse(operation, "401", "Authentication is missing or invalid");
                                        if (path.startsWith("/api/admin/") || path.startsWith("/api/cart_items/")) {
                                                addErrorResponse(operation, "403", "The authenticated user is not allowed to perform this operation");
                                        }
                                        if (path.contains("{") || path.startsWith("/api/products")
                                                        || path.startsWith("/api/orders") || path.startsWith("/api/cart")
                                                        || path.startsWith("/api/ratings") || path.startsWith("/api/reviews")
                                                        || path.startsWith("/api/payments")) {
                                                addErrorResponse(operation, "404", "A referenced resource was not found");
                                        }
                                }
                                if ("/auth/signup".equals(path)) {
                                        addErrorResponse(operation, "409", "An account already exists for this email address");
                                }
                        }));
                };
        }

        private void addErrorSchema(OpenAPI openApi) {
                if (openApi.getComponents() == null) {
                        openApi.setComponents(new Components());
                }
                ObjectSchema errorSchema = new ObjectSchema();
                errorSchema.addProperty("timestamp", new StringSchema().format("date-time")
                                .example("2026-09-27T10:15:30Z"));
                errorSchema.addProperty("status", new IntegerSchema().example(404));
                errorSchema.addProperty("error", new StringSchema().example("NOT_FOUND"));
                errorSchema.addProperty("message", new StringSchema().example("Product not found"));
                errorSchema.addProperty("path", new StringSchema().example("/api/products/101"));
                errorSchema.addProperty("validationErrors", new ObjectSchema()
                                .additionalProperties(new StringSchema()));
                openApi.getComponents().addSchemas("ApiErrorResponse", errorSchema);
        }

        private void addErrorResponse(Operation operation, String code, String description) {
                if (operation.getResponses() == null) {
                        operation.setResponses(new ApiResponses());
                }
                io.swagger.v3.oas.models.responses.ApiResponse response = operation.getResponses().get(code);
                if (response == null) {
                        response = new io.swagger.v3.oas.models.responses.ApiResponse();
                }
                response.setDescription(description);
                response.setContent(new Content().addMediaType("application/json",
                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/ApiErrorResponse"))));
                operation.getResponses().addApiResponse(code, response);
        }
}