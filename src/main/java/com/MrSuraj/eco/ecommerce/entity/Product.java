package com.MrSuraj.eco.ecommerce.entity;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Catalog product returned by product, cart, and order APIs")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Unique product identifier", example = "101", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Product display title", example = "Classic Cotton Shirt")
    private String title;

    @Schema(description = "Product description", example = "A lightweight everyday cotton shirt")
    private String Description;

    @Schema(description = "Regular price in the application's integer currency units", example = "1599")
    private int price;

    @Column(name = "discounted_price")
    @Schema(description = "Discounted price", example = "1299")
    private int discountedPrice;

    @Column(name = "discount_persent")
    @Schema(description = "Discount percentage", example = "19")
    private int discountPercent;

    @Schema(description = "Available inventory quantity", example = "25")
    private int quantity;

    @Schema(description = "Brand name", example = "Northstar")
    private String brand;

    @Schema(description = "Product color", example = "Blue")
    private String color;

    @Embedded
    @ElementCollection
    @Column(name = "sizes")
    @Schema(description = "Available sizes and per-size quantities")
    private Set<Size> sizes = new HashSet<>();

    @Column(name = "image_url")
    @Schema(description = "Product image URL", example = "https://example.com/images/classic-shirt.jpg")
    private String imageUrl;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "num_ratings")
    private int numRatings;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt;

}
