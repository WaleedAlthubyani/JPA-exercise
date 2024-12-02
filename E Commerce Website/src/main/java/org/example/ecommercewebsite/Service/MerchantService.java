package org.example.ecommercewebsite.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.BannedMerchant;
import org.example.ecommercewebsite.Model.Merchant;
import org.example.ecommercewebsite.Repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final BannedMerchantService bannedMerchantService;

    public List<Merchant> getAllMerchants(){
        return merchantRepository.findAll();
    }

    public Boolean addMerchant(Merchant merchant){
        List<BannedMerchant> banned=bannedMerchantService.getBannedMerchants();
        for (BannedMerchant bannedMerchant:banned){
            if (merchant.getName().equalsIgnoreCase(bannedMerchant.getMerchant_name()))
                return false;
        }

        merchantRepository.save(merchant);
        return true;
    }

    public Boolean updateMerchant(Integer id, Merchant merchant){
        Merchant oldMerchant = merchantRepository.getById(id);

        if (oldMerchant==null)
            return false;

        oldMerchant.setName(merchant.getName());
        merchantRepository.save(oldMerchant);
        return true;
    }

    public Boolean deleteMerchant(Integer id){
        Merchant merchant = merchantRepository.getById(id);

        if (merchant==null)
            return false;

        merchantRepository.delete(merchant);
        return true;
    }

    public boolean merchantExist(Integer id){
        List<Merchant> merchants=getAllMerchants();

        for (Merchant merchant: merchants){
            if (merchant.getId().equals(id))
                return true;
        }
        return false;
    }

    public Merchant getMerchantById(Integer id){
        List<Merchant> merchants=getAllMerchants();

        for (Merchant merchant: merchants){
            if (merchant.getId().equals(id))
                return merchant;
        }
        return null;
    }
}
