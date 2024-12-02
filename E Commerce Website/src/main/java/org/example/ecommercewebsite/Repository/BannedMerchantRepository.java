package org.example.ecommercewebsite.Repository;

import org.example.ecommercewebsite.Model.BannedMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedMerchantRepository extends JpaRepository<BannedMerchant,Integer> {
}
