package com.MrSuraj.eco.ecommerce.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@NoArgsConstructor
@Getter @Setter
@Schema(description = "Authentication result containing a JWT for subsequent protected API requests")
public class AuthResponse {
    @Schema(description = "JWT bearer token; send it in the Authorization header for protected endpoints", example = "eyJhbGciOiJIUzI1NiJ9.example.signature")
    private String jwt;

    @Schema(description = "Authentication result message", example = "SignUp Success")
    private String message;

    @Schema(description = "Persisted account role used by the frontend for navigation", example = "CUSTOMER", allowableValues = {"CUSTOMER", "ADMIN"})
    private String role;
}
