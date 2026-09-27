package com.MrSuraj.eco.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Schema(description = "Product category in the application's three-level catalog hierarchy")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Category identifier", example = "7", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Schema(description = "Category name", example = "Shirts")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id")
    @Schema(description = "Parent category when this category is nested")
    private Category parentCategory;

    @Schema(description = "Category depth in the hierarchy", example = "3")
    private int level;
}
