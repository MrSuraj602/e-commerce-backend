package com.MrSuraj.eco.ecommerce.request;

import com.MrSuraj.eco.ecommerce.entity.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Schema(description = "Catalog information used to create a product and its three-level category")
public class CreateProductRequest {
    @NotBlank
    @Schema(description = "Product display name", example = "Classic Cotton Shirt")
    private String title;

    @Schema(description = "Product description", example = "A lightweight everyday cotton shirt")
    private String description;

    @PositiveOrZero
    @Schema(description = "Regular price in the application's integer currency units", example = "1599")
    private int price;

    @PositiveOrZero
    @Schema(description = "Discounted price in the application's integer currency units", example = "1299")
    private int discountPrice;

    @PositiveOrZero
    @Schema(description = "Discount percentage stored with the product", example = "19")
    private int discountPersent;

    @PositiveOrZero
    @Schema(description = "Available inventory quantity", example = "25")
    private int quantity;

    @Schema(description = "Product brand", example = "Northstar")
    private String brand;

    @Schema(description = "Product color used by catalog filtering", example = "Blue")
    private String color;

    @Schema(description = "Available sizes and per-size quantities")
    private Set<Size> size = new HashSet<>();

    @Schema(description = "Product image URL", example = "https://example.com/images/classic-shirt.jpg")
    private String imageUrl;

    @NotBlank
    @Schema(description = "Top-level category name", example = "Men")
    private String topLevelCategory;

    @NotBlank
    @Schema(description = "Second-level category name", example = "Clothing")
    private String secondLevelCategory;

    @NotBlank
    @Schema(description = "Third-level category name", example = "Shirts")
    private String thirdLevelCategory;
}
