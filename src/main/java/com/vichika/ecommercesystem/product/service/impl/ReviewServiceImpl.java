package com.vichika.ecommercesystem.product.service.impl;

import com.vichika.ecommercesystem.exceptions.BadRequestException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.product.dto.request.ReviewRequest;
import com.vichika.ecommercesystem.product.dto.response.ReviewResponse;
import com.vichika.ecommercesystem.product.mapper.ReviewMapper;
import com.vichika.ecommercesystem.product.model.Product;
import com.vichika.ecommercesystem.product.model.Review;
import com.vichika.ecommercesystem.product.repository.ProductRepository;
import com.vichika.ecommercesystem.product.repository.ReviewRepository;
import com.vichika.ecommercesystem.product.service.ReviewService;
import com.vichika.ecommercesystem.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;
    private final AuthUtil authUtil;

    @Override
    public ReviewResponse createReview(ReviewRequest request) {

        var user = authUtil.getCurrentUser();
        var product = getProductById(request.productId());

        if (reviewRepository.existsByUserAndProduct(user,product)){
            throw new BadRequestException("You already reviewed this product");
        }

        var review = reviewMapper.toEntity(request);
        review.setProduct(product);
        review.setUser(user);

        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getProductReviews(Long productId) {

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return reviewRepository.findByProduct(product)
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

    @Override
    public ReviewResponse updateReviews(Long reviewId, ReviewRequest request) {

        var user = authUtil.getCurrentUser();
        var review = getReviewById(reviewId);

        if (!review.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Access denied");
        }

        review.setRating(request.rating());
        review.setComment(request.comment());

        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    public void deleteReviews(Long reviewId) {

        var user = authUtil.getCurrentUser();
        var review = getReviewById(reviewId);
        if (!review.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Access denied");
        }

        reviewRepository.delete(review);
    }

    private Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    private Review getReviewById(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id " + reviewId));
    }
}
