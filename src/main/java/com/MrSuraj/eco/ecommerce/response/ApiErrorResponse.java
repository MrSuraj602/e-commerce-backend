package com.MrSuraj.eco.ecommerce.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Schema(description = "Consistent error payload returned for API failures")
public record ApiErrorResponse(
        @Schema(description = "UTC time when the error was created", example = "2026-09-27T10:15:30Z") String timestamp,
        @Schema(description = "HTTP status code", example = "404") int status,
        @Schema(description = "HTTP error name", example = "NOT_FOUND") String error,
        @Schema(description = "Human-readable error message", example = "Product not found") String message,
        @Schema(description = "Request path that produced the error", example = "/api/products/101") String path,
        @Schema(description = "Field-specific validation messages; empty for non-validation errors") Map<String, String> validationErrors) {

    public static ApiErrorResponse of(HttpStatus status, String message, String path) {
        return new ApiErrorResponse(java.time.Instant.now().toString(), status.value(), status.name(), message, path, Map.of());
    }
}