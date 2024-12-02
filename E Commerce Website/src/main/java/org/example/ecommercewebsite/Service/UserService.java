package org.example.ecommercewebsite.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.ApiResponse.ApiResponse;
import org.example.ecommercewebsite.Model.*;
import org.example.ecommercewebsite.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;
    private final PurchaseHistoryService purchaseHistoryService;
    private final BestSellerService bestSellerService;
    private final ReviewService reviewService;
    private final RefundService refundService;
    private final BannedMerchantService bannedMerchantService;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public Boolean updateUser(Integer id, User user){
        User oldUser = userRepository.getById(id);

        if (oldUser==null)
            return false;


        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        oldUser.setRole(user.getRole());
        oldUser.setBalance(user.getBalance());

        userRepository.save(oldUser);
        return true;
    }

    public Boolean deleteUser(Integer id){
        User user = userRepository.getById(id);

        if (user==null)
            return false;

        userRepository.delete(user);
        return true;
    }

    public int buyProduct(Integer userId,Integer productId, Integer merchantId){
        User user = userRepository.getById(userId);

        if (user==null)
            return 0;

        Product product=productService.getProductById(productId);
        if (product==null)
            return 1;//fail product not found
        Merchant merchant=merchantService.getMerchantById(merchantId);
        if (merchant==null)
            return 2;//fail merchant not found

        MerchantStock merchantStock=null;
        for (MerchantStock m:merchantStockService.getAllMerchantStocks()){
            if (m.getMerchant_id().equals(merchantId)){
                if (m.getProduct_id().equals(productId)){
                    merchantStock=m;
                    break;
                }
            }
        }

        if (merchantStock==null)
            return 3;//fail merchant doesn't sell product

        if (merchantStock.getStock()==0)
            return 4;//fail sold out

        if (userRepository.getById(userId).getBalance()<product.getPrice())
            return 5;//fail not enough money

        //reduce stock by 1
        merchantStock.setStock(merchantStock.getStock()-1);

        //send the purchased item to the bestsellers list
        bestSellerService.bought(productId);

        //reduce the balance by the price of the product
        user.setBalance(user.getBalance()-product.getPrice());

        purchaseHistoryService.addPurchaseHistory(userId,productId);
        return 6;
    }

    public int addReviewToProduct(Integer userId,Integer productId, String review){
        return reviewService.addReview(userId,productId,review);
    }

    public int updateReview(Integer review_id,Integer user_id,Integer product_id,String review){
        return reviewService.updateReview(review_id,user_id,product_id,review);
    }

    public int RequestRefund(Integer userId,Integer productId, String message){
        if (!userExist(userId))
            return 0;
        if (!purchaseHistoryService.userPurchasedProduct(userId,productId))
            return 1;
        if (message.length()<6)
            return 2;

        refundService.addRefundRequest(userId,productId,message);
        return 3;
    }

    public ResponseEntity<ApiResponse<?>> getRefundList(Integer userId){
        if (userExist(userId))
            return ResponseEntity.status(404).body(new ApiResponse<>("User not found"));
        if (userRepository.getById(userId).getRole().equalsIgnoreCase("customer")){
            return ResponseEntity.status(401).body(new ApiResponse<>("You are not authorized to do this action"));
        }

        return ResponseEntity.status(200).body(new ApiResponse<>(refundService.getAllRefunds()));
    }

    public int decideRefund(Integer adminId,Integer refundId,Boolean decision){
        if (userExist(adminId))
            return 0;
        if (userRepository.getById(adminId).getRole().equalsIgnoreCase("customer"))
            return 1;
        if (refundService.refundExist(refundId))
            return 2;

        int result=refundService.decideOnRefund(adminId,refundId,decision);
        if (result==5){
            return 5;
        }
        Refund refund=refundService.getRefundById(refundId);
        userRepository.getById(refund.getUser_id()).setBalance(userRepository.getById(refund.getUser_id()).getBalance()+productService.getProductById(refund.getProduct_id()).getPrice());
        return 4;
    }

    public int banMerchant(Integer userId,Integer merchantId,String reason){
        if (!userExist(userId))
            return 0;
        if (userRepository.getById(userId).getRole().equalsIgnoreCase("customer"))
            return 1;
        if (!merchantService.merchantExist(merchantId))
            return 2;

        Merchant merchant=merchantService.getMerchantById(merchantId);
        merchantService.deleteMerchant(merchant.getId());

        for (MerchantStock merchantStock:merchantStockService.getAllMerchantStocks()){
            if (merchantStock.getMerchant_id().equals(merchantId)){
                merchantStockService.deleteMerchantStock(merchantStock.getId());
                break;
            }
        }

        bannedMerchantService.addBannedMerchant(merchant.getName(),reason);
        return 3;
    }

    public Boolean userExist(Integer id){
        List<User> users = userRepository.findAll();

        for (User user: users){
            if (user.getId().equals(id))
                return true;
        }

        return false;
    }
}
