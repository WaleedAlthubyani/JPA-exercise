package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.ApiResponse.ApiResponse;
import org.example.ecommercewebsite.Model.Category;
import org.example.ecommercewebsite.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/e_commerce_website/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.status(200).body(new ApiResponse<>(categoryService.getAllCategories()));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addCategory(@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }
        categoryService.addCategory(category);

        return ResponseEntity.status(201).body(new ApiResponse<>("Category created successfully"));
    }

    @PutMapping("/update/{category_id}")
    public ResponseEntity<ApiResponse<String>> updateCategory(@PathVariable Integer category_id, @RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }

        Boolean isUpdated = categoryService.updateCategory(category_id, category);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse<>("Category updated successfully"));

        return ResponseEntity.status(404).body(new ApiResponse<>("Category not found"));

    }

    @DeleteMapping("/delete/{category_id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Integer category_id) {
        boolean isDeleted = categoryService.deleteCategory(category_id);

        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse<>("Category deleted successfully"));

        return ResponseEntity.status(404).body(new ApiResponse<>("Category not found"));
    }
}
