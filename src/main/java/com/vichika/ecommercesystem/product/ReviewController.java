package com.vichika.ecommercesystem.product;

import com.vichika.ecommercesystem.common.APIResponse;
import com.vichika.ecommercesystem.product.dto.request.ReviewRequest;
import com.vichika.ecommercesystem.product.dto.response.ReviewResponse;
import com.vichika.ecommercesystem.product.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<APIResponse<ReviewResponse>> create(@Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(APIResponse.create(reviewService.createReview(request)));
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<APIResponse<List<ReviewResponse>>> getProductReview(@PathVariable Long productId) {
        return ResponseEntity.ok(APIResponse.ok(reviewService.getProductReviews(productId)));
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<APIResponse<ReviewResponse>> update(@PathVariable Long reviewId,
                                                 @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(APIResponse.ok(reviewService.updateReviews(reviewId, request)));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> delete(@PathVariable Long reviewId) {
        reviewService.deleteReviews(reviewId);
        return ResponseEntity.noContent().build();
    }

}
