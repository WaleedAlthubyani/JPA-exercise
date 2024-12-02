package org.example.ecommercewebsite.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer review_id;

    @Column(columnDefinition = "varchar(255) not null")
    private String review;

    @Column(columnDefinition = "int not null")
    private Integer user_id;

    @Column(columnDefinition = "int not null")
    private Integer product_id;
}
