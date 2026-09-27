package com.MrSuraj.eco.ecommerce.request;

import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Schema(description = "A customer's written review for a catalog product")
public class ReviewRequest {
    @NotNull
    @Schema(description = "Identifier of the product being reviewed", example = "101")
    private Long productId;

    @NotBlank
    @Schema(description = "Review text", example = "Comfortable fit and good fabric quality.")
    private String review;
}
