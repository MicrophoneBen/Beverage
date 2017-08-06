package com.github.morotsman.beverage.review;

import java.util.Optional;


public interface ReviewService {
    
    Optional<ReviewDto> createReview(String username,ReviewDto review);
    
    Optional<ReviewDto> getReview(String username,long reviewId);
    
    Optional<ReviewDto> updateReview(String username, ReviewDto review);
    
    Optional<Boolean> deleteReview(String username, long reviewId);
    
    Iterable<ReviewDto> getReviews(String username, String query, final int page);

    void deleteAllReviews();
    
}
