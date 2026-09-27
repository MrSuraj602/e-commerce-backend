package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.config.JwtProvider;
import com.MrSuraj.eco.ecommerce.entity.Cart;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.repo.UserRepository;
import com.MrSuraj.eco.ecommerce.request.LoginRequest;
import com.MrSuraj.eco.ecommerce.response.AuthResponse;
import com.MrSuraj.eco.ecommerce.service.CartService;
import com.MrSuraj.eco.ecommerce.service.CustomeUserServiceImplementation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Public customer registration and sign-in; successful requests return a JWT for protected API calls")
public class AuthController {
    private final CustomeUserServiceImplementation customeUserServiceImplementation;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final CartService cartService;

    @PostMapping("/signup")
        @Operation(summary = "Register a customer account", description = "Creates a customer account and an initial cart, then returns a JWT. Use this when a new customer completes the storefront registration form; no existing token is required.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account and cart created; JWT returned"),
            @ApiResponse(responseCode = "400", description = "Request body could not be read"),
            @ApiResponse(responseCode = "500", description = "Registration failed; duplicate-email exceptions are not translated by a project exception handler")
        })
        public ResponseEntity<AuthResponse> createUserHandler(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Customer registration fields: first name, last name, email, and password", required = true)
            @Parameter(description = "New customer account details") @RequestBody User user) throws UserException{
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExist = userRepository.findByEmail(email);

        if(isEmailExist != null){
            throw new UserException("Email is Already Used With Another Account");
        }
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(encoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User savedUser = userRepository.save(createdUser);
        Cart cart = cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("SignUp Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
        @Operation(summary = "Sign in a customer", description = "Checks the supplied email and password and returns a JWT. Use this when an existing customer signs in; paste the returned token into Swagger UI's Authorize dialog for protected operations.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Credentials accepted and JWT returned"),
            @ApiResponse(responseCode = "400", description = "Request body could not be read"),
            @ApiResponse(responseCode = "401", description = "Credentials were rejected"),
            @ApiResponse(responseCode = "500", description = "Authentication service failed")
        })
        public ResponseEntity<AuthResponse> loginUserHandler(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Email and password for an existing customer account", required = true)
            @Parameter(description = "Customer sign-in credentials") @RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authentication(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    private Authentication authentication(String username,String password){
        UserDetails userDetails = customeUserServiceImplementation.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid Username");

        }
        if(!encoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
