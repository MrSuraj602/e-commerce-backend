package com.MrSuraj.eco.ecommerce.request;

import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Schema(description = "A customer's rating for a catalog product")
public class RatingRequest {
    @NotNull
    @Schema(description = "Identifier of the product being rated", example = "101")
    private Long productId;

    @Schema(description = "Numeric rating value; the current service does not enforce a range", example = "4.5")
    private double rating;
}
