package com.MrSuraj.eco.ecommerce.entity;

import com.MrSuraj.eco.ecommerce.service.ProductService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Getter
@Setter
@Schema(description = "A product line in a customer's cart")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Cart-line identifier", example = "501", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Cart cart;

    @ManyToOne
    @Schema(description = "Selected catalog product")
    private Product product;

    @Schema(description = "Selected size", example = "M")
    private String size;

    @Schema(description = "Number of units in this cart line", example = "2")
    private int quantity;

    @Schema(description = "Price stored for this cart line", example = "1299")
    private Integer price;

    private Integer discountedPrice;

    private Long userId;
}
