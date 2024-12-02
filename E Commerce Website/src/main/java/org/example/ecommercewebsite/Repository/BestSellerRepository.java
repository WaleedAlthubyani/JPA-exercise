package org.example.ecommercewebsite.Repository;

import org.example.ecommercewebsite.Model.BestSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestSellerRepository extends JpaRepository<BestSeller,Integer> {
}
