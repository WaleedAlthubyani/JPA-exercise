package org.example.ecommercewebsite.Repository;

import org.example.ecommercewebsite.Model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund,Integer> {
}
