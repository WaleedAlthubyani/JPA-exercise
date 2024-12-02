package org.example.ecommercewebsite.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.Review;
import org.example.ecommercewebsite.Model.User;
import org.example.ecommercewebsite.Repository.ReviewRepository;
import org.example.ecommercewebsite.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PurchaseHistoryService purchaseHistoryService;

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public int addReview(Integer userId, Integer productId,String review){
        if (!userExists(userId))
            return 0;
        else if (!purchaseHistoryService.userPurchasedProduct(userId,productId))
            return 1;

        Review add_review = new Review();
        add_review.setUser_id(userId);
        add_review.setProduct_id(productId);
        add_review.setReview(review);
        reviewRepository.save(add_review);
        return 2;
    }

    public int updateReview(Integer id,Integer userId,Integer productId,String review){
        if (!userExists(userId))
            return 0;
        else if (!purchaseHistoryService.userPurchasedProduct(userId,productId))
            return 1;

        Review oldReview=reviewRepository.getById(id);

        if (oldReview==null)
            return 2;

        oldReview.setProduct_id(productId);
        oldReview.setUser_id(userId);
        oldReview.setReview(review);

        reviewRepository.save(oldReview);
        return 3;
    }

    public Boolean deleteReview(Integer id){
        Review review=reviewRepository.getById(id);

        if (review==null)
            return false;

        reviewRepository.delete(review);
        return true;
    }

    public Boolean userExists(Integer userId){
        List<User> users = userRepository.findAll();

        for (User user:users){
            if (user.getId().equals(userId))
                return true;
        }

        return false;
    }

}
