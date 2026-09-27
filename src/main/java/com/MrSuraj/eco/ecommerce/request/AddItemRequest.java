package com.MrSuraj.eco.ecommerce.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Schema(description = "Product selection and quantity to add to the authenticated customer's cart")
public class AddItemRequest {
    @NotNull
    @Schema(description = "Identifier of the catalog product", example = "101")
    private Long productId;

    @NotBlank
    @Schema(description = "Selected product size", example = "M")
    private String size;

    @Positive
    @Schema(description = "Number of units requested", example = "2")
    private int quantity;

    @Schema(description = "Price supplied for the cart item", example = "1299")
    private Integer price;
}
