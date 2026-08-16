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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review> createRating(@RequestBody ReviewRequest req,
                                               @RequestHeader("Authorization")String jwt)throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req,user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductsRating(@PathVariable Long productId)throws UserException,ProductException{

        List<Review> reviews = reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews,HttpStatus.ACCEPTED);
    }

}


