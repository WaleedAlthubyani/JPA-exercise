package org.example.ecommercewebsite.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;


@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Check(constraints = "length(name)>=4")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Please enter the category's name")
    @Size(min = 4,message = "Name can't be less than 4 characters in length")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String name;
}
