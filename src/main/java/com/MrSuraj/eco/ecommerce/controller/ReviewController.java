package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.Rating;
import com.MrSuraj.eco.ecommerce.entity.Review;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.request.RatingRequest;
import com.MrSuraj.eco.ecommerce.request.ReviewRequest;
import com.MrSuraj.eco.ecommerce.service.RatingService;
import com.MrSuraj.eco.ecommerce.service.ReviewService;
import com.MrSuraj.eco.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "Create customer product reviews and retrieve reviews for a product")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
        @Operation(summary = "Review a product", description = "Stores written feedback for a catalog product on behalf of the authenticated customer. Use this when the customer submits a product review from the storefront or order experience.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Review created"),
            @ApiResponse(responseCode = "400", description = "Review body could not be read"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Review creation failed")
        })
    public ResponseEntity<Review> createRating(
                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product identifier and written review text", required = true)
                                               @Parameter(description = "Review submitted by the customer") @RequestBody ReviewRequest req,
                                               @Parameter(hidden = true) @RequestHeader("Authorization")String jwt)throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req,user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
        @Operation(summary = "List reviews for a product", description = "Retrieves all written reviews for the selected product. Use this when loading customer feedback on a product detail page. This endpoint does not declare a JWT header parameter and is still protected by the application's /api/** security rule.")
        @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Product reviews returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Review lookup failed")
        })
        public ResponseEntity<List<Review>> getProductsRating(@Parameter(description = "Unique identifier of the product whose reviews should be listed", example = "101") @PathVariable Long productId)throws UserException,ProductException{

        List<Review> reviews = reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews,HttpStatus.ACCEPTED);
    }

}


