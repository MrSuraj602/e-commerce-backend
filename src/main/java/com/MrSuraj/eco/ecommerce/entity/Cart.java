package com.MrSuraj.eco.ecommerce.entity;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Current customer's cart, including its lines and aggregate totals")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Cart identifier", example = "701", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "cart_items")
    @Schema(description = "Cart line items")
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "total_price")
    @Schema(description = "Cart total before discounts", example = "3198.0")
    private double totalPrice;

    @Column(name = "total_item")
    @Schema(description = "Total number of units in the cart", example = "2")
    private int totalItem;

    private int totalDiscountedPrice;
    private int discounte;
}
