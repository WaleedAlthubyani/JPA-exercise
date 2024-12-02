package org.example.ecommercewebsite.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.ApiResponse.ApiResponse;
import org.example.ecommercewebsite.Model.Review;
import org.example.ecommercewebsite.Service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/e_commerce_website/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<Review>>> getAllReviews(){
        return ResponseEntity.status(200).body(new ApiResponse<>(reviewService.getAllReviews()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteReview(Integer review_id){
        boolean isDeleted = reviewService.deleteReview(review_id);

        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse<>("Review deleted successfully"));

        return ResponseEntity.status(404).body(new ApiResponse<>("Review not found"));
    }
}
