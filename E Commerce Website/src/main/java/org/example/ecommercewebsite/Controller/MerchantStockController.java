package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.ApiResponse.ApiResponse;
import org.example.ecommercewebsite.Model.MerchantStock;
import org.example.ecommercewebsite.Service.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/e_commerce_website/merchant_stock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<MerchantStock>>> getAllMerchantStocks(){
        return ResponseEntity.status(200).body(new ApiResponse<>(merchantStockService.getAllMerchantStocks()));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }
        int result = merchantStockService.addMerchantStock(merchantStock);

        return switch (result) {
            case 0 -> ResponseEntity.status(404).body(new ApiResponse<>("Merchant not found"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse<>("Product not found"));
            default -> ResponseEntity.status(201).body(new ApiResponse<>("Merchant stock created successfully"));
        };
    }

    @PutMapping("/update/{merchant_stock_id}")
    public ResponseEntity<ApiResponse<String>> updateMerchantStock(@PathVariable Integer merchant_stock_id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }

        int result = merchantStockService.updateMerchantStock(merchant_stock_id, merchantStock);
        return switch (result) {
            case 0 -> ResponseEntity.status(404).body(new ApiResponse<>("Merchant not found"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse<>("Product not found"));
            case 3 -> ResponseEntity.status(404).body(new ApiResponse<>("Merchant stock not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse<>("Merchant stock updated successfully"));
        };
    }

    @DeleteMapping("/delete/{merchant_stock_id}")
    public ResponseEntity<ApiResponse<String>> deleteMerchant(@PathVariable Integer merchant_stock_id) {
        boolean isDeleted = merchantStockService.deleteMerchantStock(merchant_stock_id);

        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse<>("Merchant stock deleted successfully"));

        return ResponseEntity.status(404).body(new ApiResponse<>("Merchant stock not found"));
    }

}
