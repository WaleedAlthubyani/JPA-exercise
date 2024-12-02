package org.example.ecommercewebsite.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.BestSeller;
import org.example.ecommercewebsite.Model.Product;
import org.example.ecommercewebsite.Repository.BestSellerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BestSellerService {

    private final BestSellerRepository bestSellerRepository;
    private final ProductService productService;

    public List<Product> getBestSellers(Integer limit){
        List<Product> limitedBestSellers=new ArrayList<>();
        List<BestSeller> bestSellers=bestSellerRepository.findAll(Sort.by(Sort.Order.desc("sold_count")));

        for (int i = 0; i < bestSellers.size(); i++) {
            if (i==limit)
                break;

            limitedBestSellers.add(productService.getProductById(bestSellers.get(i).getProduct_id()));
        }

        return limitedBestSellers;
    }

    public void bought(Integer productId){
        for (BestSeller bestSeller:bestSellerRepository.findAll()){
            if (bestSeller.getProduct_id().equals(productId)){
                bestSeller.setSold_count(bestSeller.getSold_count()+1);
                return;
            }
        }

        BestSeller bestSeller = new BestSeller();
        bestSeller.setProduct_id(productId);
        bestSeller.setSold_count(1);
        bestSellerRepository.save(bestSeller);
    }
}
