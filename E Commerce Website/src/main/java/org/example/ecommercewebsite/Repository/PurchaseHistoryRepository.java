package org.example.ecommercewebsite.Repository;

import org.example.ecommercewebsite.Model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory,Integer> {
}
