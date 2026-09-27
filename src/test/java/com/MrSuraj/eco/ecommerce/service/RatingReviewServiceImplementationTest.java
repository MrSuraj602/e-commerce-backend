package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.entity.Product;
import com.MrSuraj.eco.ecommerce.entity.Rating;
import com.MrSuraj.eco.ecommerce.entity.Review;
import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.repo.ProductRepository;
import com.MrSuraj.eco.ecommerce.repo.RatingRepository;
import com.MrSuraj.eco.ecommerce.repo.ReviewRepository;
import com.MrSuraj.eco.ecommerce.request.RatingRequest;
import com.MrSuraj.eco.ecommerce.request.ReviewRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingReviewServiceImplementationTest {
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private RatingServiceImplementation ratingService;
    @InjectMocks
    private ReviewServiceImplementation reviewService;

    @Test
    void shouldCreateRatingForResolvedProductAndUser() throws Exception {
        Product product = new Product();
        User user = new User();
        RatingRequest request = new RatingRequest();
        request.setProductId(14L);
        request.setRating(4.5);
        when(productService.findProductById(14L)).thenReturn(product);
        when(ratingRepository.save(any(Rating.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Rating rating = ratingService.createRating(request, user);

        assertSame(product, rating.getProduct());
        assertSame(user, rating.getUser());
        assertEquals(4.5, rating.getRating());
    }

    @Test
    void shouldCreateReviewForResolvedProductAndUser() throws Exception {
        Product product = new Product();
        User user = new User();
        ReviewRequest request = new ReviewRequest();
        request.setProductId(14L);
        request.setReview("Good fit and fabric.");
        when(productService.findProductById(14L)).thenReturn(product);
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Review review = reviewService.createReview(request, user);

        assertSame(product, review.getProduct());
        assertSame(user, review.getUser());
        assertEquals("Good fit and fabric.", review.getReview());
    }
}