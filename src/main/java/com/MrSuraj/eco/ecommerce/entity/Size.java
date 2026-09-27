package com.MrSuraj.eco.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "A product size and the inventory quantity available for that size")
public class Size {
    @Schema(description = "Size label", example = "M")
    private String name;

    @Schema(description = "Quantity available in this size", example = "8")
    private int quantity;

}
