package com.MrSuraj.eco.ecommerce.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class RatingRequest {
    private Long productId;
    private double rating;
}
