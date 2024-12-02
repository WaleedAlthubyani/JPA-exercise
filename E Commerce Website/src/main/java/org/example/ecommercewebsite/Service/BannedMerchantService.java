package org.example.ecommercewebsite.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.BannedMerchant;
import org.example.ecommercewebsite.Repository.BannedMerchantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannedMerchantService {

    private final BannedMerchantRepository bannedMerchantRepository;

    public List<BannedMerchant> getBannedMerchants(){
        return bannedMerchantRepository.findAll();
    }

    public void addBannedMerchant(String merchantName, String reason){
        BannedMerchant bannedMerchant = new BannedMerchant();

        bannedMerchant.setMerchant_name(merchantName);
        bannedMerchant.setReason(reason);

        bannedMerchantRepository.save(bannedMerchant);
    }
}
