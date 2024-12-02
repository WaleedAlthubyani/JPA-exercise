package org.example.ecommercewebsite.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Check(constraints = "price>0")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Please enter the name of your product")
    @Size(min = 4, message = "Name can't be less than 4 characters in length")
    @Column(columnDefinition = "varchar(255) not null unique")
    private String name;

    @NotNull(message = "Please enter the product's price")
    @Positive(message = "The product's price must be a positive number")
    @Column(columnDefinition = "double not null")
    private Double price;

    @NotEmpty(message = "Please enter the product's category's ID")
    @Column(columnDefinition = "int not null")
    private Integer categoryId;

}
