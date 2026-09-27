package com.MrSuraj.eco.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Schema(description = "Shipping address submitted during checkout and returned with an order")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Address identifier when returned by the API", example = "301", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Column(name = "first_name")
    @Schema(description = "Recipient first name", example = "Alex")
    private String firstName;

    @Column(name = "last_name")
    @Schema(description = "Recipient last name", example = "Morgan")
    private String lastName;

    @Column(name = "street_address")
    @Schema(description = "Street and building address", example = "12 Example Road")
    private String streetAddress;

    @Column(name = "city")
    @Schema(description = "City", example = "Pune")
    private String city;

    @Column(name = "state")
    @Schema(description = "State or region", example = "Maharashtra")
    private String state;

    @Column(name = "zip_code")
    @Schema(description = "Postal or ZIP code", example = "411001")
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Schema(description = "Recipient contact number", example = "+91-9000000000")
    private String mobile;
}
