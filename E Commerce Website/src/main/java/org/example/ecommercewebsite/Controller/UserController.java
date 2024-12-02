package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.ApiResponse.ApiResponse;
import org.example.ecommercewebsite.Model.User;
import org.example.ecommercewebsite.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/e_commerce_website/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(){
        return ResponseEntity.status(200).body(new ApiResponse<>(userService.getAllUsers()));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ApiResponse<>("User created successfully"));
    }

    @PutMapping("/update/{user_id}")
    public ResponseEntity<ApiResponse<String>> updateUser(@PathVariable Integer user_id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }

        Boolean isUpdated = userService.updateUser(user_id, user);

        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse<>("User updated successfully"));

        return ResponseEntity.status(404).body(new ApiResponse<>("User not found"));
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Integer user_id) {
        boolean isDeleted = userService.deleteUser(user_id);

        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse<>("User deleted successfully"));

        return ResponseEntity.status(404).body(new ApiResponse<>("User not found"));
    }

    @PostMapping("/buy-product/{user_id}/{product_id}/{merchant_id}")
    public ResponseEntity<ApiResponse<String>> buyProduct(@PathVariable Integer user_id,@PathVariable Integer product_id,@PathVariable Integer merchant_id){
        int result = userService.buyProduct(user_id,product_id,merchant_id);

        return switch (result) {
            case 0 -> ResponseEntity.status(404).body(new ApiResponse<>("User not found"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse<>("Product not found"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse<>("Merchant not found"));
            case 3 -> ResponseEntity.status(400).body(new ApiResponse<>("Merchant doesn't sell this product"));
            case 4 -> ResponseEntity.status(400).body(new ApiResponse<>("Product is sold out"));
            case 5 -> ResponseEntity.status(400).body(new ApiResponse<>("User doesn't have enough money"));
            default -> ResponseEntity.status(200).body(new ApiResponse<>("product bought successfully"));
        };
    }

    @PostMapping("/add-review/{user_id}/{product_id}/{review}")
    public ResponseEntity<ApiResponse<String>> addReview(@PathVariable Integer user_id,@PathVariable Integer product_id,@PathVariable String review){
        int result = userService.addReviewToProduct(user_id,product_id,review);

        return switch (result) {
            case 0 -> ResponseEntity.status(404).body(new ApiResponse<>("User not found"));
            case 1 -> ResponseEntity.status(400).body(new ApiResponse<>("User did not buy this product"));
            default -> ResponseEntity.status(404).body(new ApiResponse<>("Review added successfully"));
        };
    }

    @PutMapping("/update-review/{review_id}/{user_id}/{product_id}/{review}")
    public ResponseEntity<ApiResponse<String>> updateReview(@PathVariable Integer review_id,@PathVariable Integer user_id,@PathVariable Integer product_id,@PathVariable String review){
        int result = userService.updateReview(review_id,user_id,product_id,review);

        return switch (result) {
            case 0 -> ResponseEntity.status(404).body(new ApiResponse<>("User not found"));
            case 1 -> ResponseEntity.status(400).body(new ApiResponse<>("User didn't buy this product"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse<>("Old review not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse<>("Review updated successfully"));
        };
    }

    @PostMapping("request-refund/{user_id}/{product_id}/{message}")
    public ResponseEntity<ApiResponse<String>> RequestRefund(@PathVariable Integer user_id,@PathVariable Integer product_id,@PathVariable String message){
        int result = userService.RequestRefund(user_id,product_id,message);

        return switch (result) {
            case 0 -> ResponseEntity.status(404).body(new ApiResponse<>("User not found"));
            case 1 -> ResponseEntity.status(400).body(new ApiResponse<>("User didn't buy this product"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse<>("Refund message too short must be 6 characters or longer"));
            default -> ResponseEntity.status(201).body(new ApiResponse<>("Request created successfully"));
        };
    }

    @GetMapping("/get-refund-list/{user_id}")
    public ResponseEntity<ApiResponse<?>> getRefundList(@PathVariable Integer user_id){
        return userService.getRefundList(user_id);
    }

    @PostMapping("/decide-refund/{admin_id}/{refund_id}/{decision}")
    public ResponseEntity<ApiResponse<?>> decideRefund(@PathVariable Integer admin_id, @PathVariable Integer refund_id,@PathVariable Boolean decision){
        int result = userService.decideRefund(admin_id,refund_id,decision);

        return switch (result) {
            case 0 -> ResponseEntity.status(404).body(new ApiResponse<>("User not found"));
            case 1 -> ResponseEntity.status(401).body(new ApiResponse<>("You are not authorized to do this action"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse<>("Refund request not found"));
            case 3 -> ResponseEntity.status(400).body(new ApiResponse<>("You can't decide on your own requests"));
            case 5 -> ResponseEntity.status(200).body(new ApiResponse<>("Refund refused successfully"));
            default -> ResponseEntity.status(200).body(new ApiResponse<>("Refund accepted successfully"));
        };
    }

    @DeleteMapping("/ban-merchant/{user_id}/{merchant_id}/{reason}")
    public ResponseEntity<ApiResponse<?>> banMerchant(@PathVariable Integer user_id, @PathVariable Integer merchant_id,@PathVariable String reason){
        int result = userService.banMerchant(user_id,merchant_id,reason);
        return switch (result) {
            case 0 -> ResponseEntity.status(404).body(new ApiResponse<>("User not found"));
            case 1 -> ResponseEntity.status(401).body(new ApiResponse<>("User not authorized to do this action"));
            case 2 -> ResponseEntity.status(404).body(new ApiResponse<>("Merchant not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse<>("Merchant banned successfully"));
        };
    }
}
