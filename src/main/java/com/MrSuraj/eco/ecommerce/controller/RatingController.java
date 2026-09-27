package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.Rating;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.request.RatingRequest;
import com.MrSuraj.eco.ecommerce.service.RatingService;
import com.MrSuraj.eco.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Ratings", description = "Create customer product ratings and retrieve ratings for a product")
@SecurityRequirement(name = "bearerAuth")
public class RatingController {
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
        @Operation(summary = "Rate a product", description = "Stores a rating for a catalog product on behalf of the authenticated customer. Use this when a customer submits a product rating from the product or order experience. The current service does not enforce a numeric rating range.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rating created"),
            @ApiResponse(responseCode = "400", description = "Rating body could not be read"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Rating creation failed")
        })
    public ResponseEntity<Rating> createRating(
                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product identifier and rating value", required = true)
                                               @Parameter(description = "Rating submitted by the customer") @RequestBody RatingRequest req,
                                               @Parameter(hidden = true) @RequestHeader("Authorization")String jwt)throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(req,user);

        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }



    @GetMapping("/product/{productId}")
        @Operation(summary = "List ratings for a product", description = "Retrieves ratings associated with the selected product. Use this when rendering the product's rating summary or rating list. The current API requires a JWT and responds with CREATED for this GET operation.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product ratings returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Rating lookup failed")
        })
        public ResponseEntity<List<Rating>> getProductsRating(@Parameter(description = "Unique identifier of the product whose ratings should be listed", example = "101") @PathVariable Long productId,
                                                          @Parameter(hidden = true) @RequestHeader("Authorization")String jwt)throws UserException,ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProductRating(productId);
        return new ResponseEntity<>(ratings,HttpStatus.CREATED);
    }
}
