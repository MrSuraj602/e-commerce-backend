package com.MrSuraj.eco.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Schema(description = "Customer profile returned by profile and nested order resources. Password is write-only; stored payment-card information is excluded from OpenAPI schemas.")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Customer identifier", example = "42", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank
    @Schema(description = "Customer first name", example = "Alex")
    private String firstName;

    @NotBlank
    @Schema(description = "Customer last name", example = "Morgan")
    private String lastName;

    @NotBlank
    @Schema(description = "Account password supplied only during registration; never returned in documented responses", example = "example-password", format = "password", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @NotBlank
    @Email
    @Schema(description = "Customer account email", example = "customer@example.com")
    private String email;
    private String role;
    private String mobile;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    @Embedded
    @ElementCollection
    @CollectionTable(name="payment_information",joinColumns = @JoinColumn(name = "user_id"))
    @Schema(hidden = true)
    private List<PaymentInformation>  paymentInformation = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> rating = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> review = new ArrayList<>();

    private LocalDateTime createdAt;
}
