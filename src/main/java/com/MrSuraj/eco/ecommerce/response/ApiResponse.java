package com.MrSuraj.eco.ecommerce.response;

import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "Simple operation result returned by cart and administrative actions")
public class ApiResponse {
    @Schema(description = "Human-readable result message", example = "item added to cart")
    private String message;

    @Schema(description = "Whether the operation completed successfully", example = "true")
    private boolean status;
}
