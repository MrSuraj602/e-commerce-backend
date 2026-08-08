package com.MrSuraj.eco.ecommerce.request;

import com.MrSuraj.eco.ecommerce.entity.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CreateProductRequest {
    private String title;
    private String description;
    private int price;

    private int discountPrice;
    private int discountPersent;
    private int quantity;

    private String brand;
    private String color;

    private Set<Size> size = new HashSet<>();

    private String imageUrl;
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;
}
