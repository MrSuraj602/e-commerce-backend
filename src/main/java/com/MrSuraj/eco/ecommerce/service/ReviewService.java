package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.entity.Review;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(ReviewRequest req, User user)throws ProductException;
    List<Review> getAllReview(Long productId);
}
