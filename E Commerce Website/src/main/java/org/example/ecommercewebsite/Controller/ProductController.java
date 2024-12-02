package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.ApiResponse.ApiResponse;
import org.example.ecommercewebsite.Model.Product;
import org.example.ecommercewebsite.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/e_commerce_website/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(){
        return ResponseEntity.status(200).body(new ApiResponse<>(productService.getAllProducts()));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }
        Boolean isCreated = productService.addProduct(product);

        if (isCreated)
            return ResponseEntity.status(201).body(new ApiResponse<>("Product created successfully"));

        return ResponseEntity.status(404).body(new ApiResponse<>("Category not found"));
    }

    @PutMapping("/update/{product_id}")
    public ResponseEntity<ApiResponse<String>> updateProduct(@PathVariable Integer product_id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }

        int result = productService.updateProduct(product_id, product);
        return switch (result) {
            case 0 -> ResponseEntity.status(404).body(new ApiResponse<>("Category not found"));
            case 1 -> ResponseEntity.status(404).body(new ApiResponse<>("Product not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse<>("Product updated successfully"));
        };
    }

    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Integer product_id) {
        boolean isDeleted = productService.deleteProduct(product_id);

        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse<>("Product deleted successfully"));

        return ResponseEntity.status(404).body(new ApiResponse<>("Product not found"));
    }
}
