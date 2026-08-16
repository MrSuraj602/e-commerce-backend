package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.Rating;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.request.RatingRequest;
import com.MrSuraj.eco.ecommerce.service.RatingService;
import com.MrSuraj.eco.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
                                               @RequestHeader("Authorization")String jwt)throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(req,user);

        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }



    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId,
                                                          @RequestHeader("Authorization")String jwt)throws UserException,ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProductRating(productId);
        return new ResponseEntity<>(ratings,HttpStatus.CREATED);
    }
}
