package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.ApiResponse.ApiResponse;
import org.example.ecommercewebsite.Model.Merchant;
import org.example.ecommercewebsite.Service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/e_commerce_website/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<Merchant>>> getAllMerchants(){
        return ResponseEntity.status(200).body(new ApiResponse<>(merchantService.getAllMerchants()));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }
        boolean isCreated=merchantService.addMerchant(merchant);
        if (isCreated)
            return ResponseEntity.status(201).body(new ApiResponse<>("Merchant created successfully"));

        return ResponseEntity.status(400).body(new ApiResponse<>("Merchant is on the banned list"));
    }

    @PutMapping("/update/{merchant_id}")
    public ResponseEntity<ApiResponse<String>> updateMerchant(@PathVariable Integer merchant_id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }

        Boolean isUpdated = merchantService.updateMerchant(merchant_id, merchant);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse<>("Merchant updated successfully"));

        return ResponseEntity.status(404).body(new ApiResponse<>("Merchant not found"));
    }

    @DeleteMapping("/delete/{merchant_id}")
    public ResponseEntity<ApiResponse<String>> deleteMerchant(@PathVariable Integer merchant_id) {
        boolean isDeleted = merchantService.deleteMerchant(merchant_id);

        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse<>("Merchant deleted successfully"));

        return ResponseEntity.status(404).body(new ApiResponse<>("Merchant not found"));
    }


}
