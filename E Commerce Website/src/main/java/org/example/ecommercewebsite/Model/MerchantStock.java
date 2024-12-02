package org.example.ecommercewebsite.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Check(constraints = "stock>=11")
public class MerchantStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    private Integer product_id;

    @Column(columnDefinition = "int not null")
    private Integer merchant_id;

    @NotNull(message = "Please enter the stock's quantity")
    @Positive(message = "stock can only be a positive number")
    @Min(value = 11,message = "Starting stocks must be more than 10")
    @Column(columnDefinition = "int not null")
    private Integer stock;
}
