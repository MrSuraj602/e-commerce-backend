package com.MrSuraj.eco.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Written product review submitted by a customer")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Review identifier", example = "802", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Review text", example = "Comfortable fit and good fabric quality.")
    private String review;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

}
