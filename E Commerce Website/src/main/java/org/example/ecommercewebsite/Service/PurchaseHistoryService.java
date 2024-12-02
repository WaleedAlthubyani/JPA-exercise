package org.example.ecommercewebsite.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.PurchaseHistory;
import org.example.ecommercewebsite.Repository.PurchaseHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;


    public void addPurchaseHistory(Integer userId,Integer productId){
        List<PurchaseHistory> purchases= getUserPurchaseHistory(userId);

        for (PurchaseHistory purchaseHistory:purchases){
            if (purchaseHistory.getProduct_id().equals(productId)){
                return;
            }
        }
        PurchaseHistory purchaseHistory=new PurchaseHistory();
        purchaseHistory.setProduct_id(productId);
        purchaseHistory.setUser_id(userId);
        purchaseHistoryRepository.save(purchaseHistory);
    }

    public List<PurchaseHistory> getUserPurchaseHistory(Integer userId){
        List<PurchaseHistory> purchaseHistories=new LinkedList<>();

        for (PurchaseHistory purchaseHistory:purchaseHistoryRepository.findAll()){
            if (purchaseHistory.getUser_id().equals(userId))
                purchaseHistories.add(purchaseHistory);
        }

        return purchaseHistories;
    }

    public Boolean userPurchasedProduct(Integer userId, Integer productId){
        for (PurchaseHistory purchaseHistory:getUserPurchaseHistory(userId)){
            if (purchaseHistory.getProduct_id().equals(productId))
                return true;
        }

        return false;
    }
}
