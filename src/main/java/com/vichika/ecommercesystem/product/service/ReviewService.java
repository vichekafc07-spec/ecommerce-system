package com.vichika.ecommercesystem.product.service;

import com.vichika.ecommercesystem.product.dto.request.ReviewRequest;
import com.vichika.ecommercesystem.product.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest request);

    List<ReviewResponse> getProductReviews(Long productId);

    ReviewResponse updateReviews(Long reviewId, ReviewRequest request);

    void deleteReviews(Long reviewId);
}
