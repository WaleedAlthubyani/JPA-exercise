package org.example.ecommercewebsite.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.ApiResponse.ApiResponse;
import org.example.ecommercewebsite.Model.Product;
import org.example.ecommercewebsite.Service.BestSellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/e_commerce_website/best_seller")
@RequiredArgsConstructor
public class BestSellerController {

    private final BestSellerService bestSellerService;

    @GetMapping("/get_best_sellers/{limit}")
    public ResponseEntity<ApiResponse<List<Product>>> getBestSellers(@PathVariable Integer limit){
        return ResponseEntity.status(200).body(new ApiResponse<>(bestSellerService.getBestSellers(limit)));
    }
}
