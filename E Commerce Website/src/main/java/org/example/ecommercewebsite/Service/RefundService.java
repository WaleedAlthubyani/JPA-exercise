package org.example.ecommercewebsite.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.Refund;
import org.example.ecommercewebsite.Repository.RefundRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundRepository refundRepository;

    public List<Refund> getAllRefunds(){
        return refundRepository.findAll();
    }

    public void addRefundRequest(Integer userId, Integer productId,String message){

        Refund refund=new Refund();
        refund.setUser_id(userId);
        refund.setProduct_id(productId);
        refund.setMessage(message);
        refund.setStatus(null);
        refund.setDecision_maker(null);

        refundRepository.save(refund);
    }

    public int decideOnRefund(Integer userId,Integer refundId,Boolean status){
        if (refundRepository.getById(refundId).getUser_id().equals(userId))
            return 3;

        Refund refund = refundRepository.getById(refundId);


        refund.setStatus(status);
        refund.setDecision_maker(userId);

        if (status)
            return 4;

        return 5;
    }

    public Refund getRefundById(Integer refundId){
        return refundRepository.getById(refundId);
    }

    public Boolean refundExist(Integer refundId){
        return refundRepository.existsById(refundId);
    }
}
