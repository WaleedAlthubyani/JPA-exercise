package org.example.ecommercewebsite.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.MerchantStock;
import org.example.ecommercewebsite.Repository.MerchantStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final MerchantStockRepository merchantStockRepository;
    private final ProductService productService;
    private final MerchantService merchantService;

    public List<MerchantStock> getAllMerchantStocks(){
        return merchantStockRepository.findAll();
    }

    public int addMerchantStock(MerchantStock merchantStock){
        if (!merchantService.merchantExist(merchantStock.getMerchant_id()))
            return 0;
        if (!productService.productExist(merchantStock.getProduct_id()))
            return 1;

        merchantStockRepository.save(merchantStock);
        return 2;
    }

    public int updateMerchantStock(Integer id, MerchantStock merchantStock){
        if (!merchantService.merchantExist(merchantStock.getMerchant_id()))
            return 0;
        if (!productService.productExist(merchantStock.getProduct_id()))
            return 1;

        MerchantStock oldMerchantStock = merchantStockRepository.getById(id);
        if (oldMerchantStock==null)
            return 2;

        oldMerchantStock.setMerchant_id(merchantStock.getMerchant_id());
        oldMerchantStock.setStock(merchantStock.getStock());
        oldMerchantStock.setProduct_id(merchantStock.getProduct_id());

        merchantStockRepository.save(oldMerchantStock);
        return 3;
    }

    public Boolean deleteMerchantStock(Integer id){
        MerchantStock merchantStock=merchantStockRepository.getById(id);

        if (merchantStock==null)
            return false;

        merchantStockRepository.delete(merchantStock);
        return true;
    }

}
