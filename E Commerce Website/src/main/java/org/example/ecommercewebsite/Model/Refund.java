package org.example.ecommercewebsite.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Check(constraints = "length(message)>6")
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Please provide a request message")
    @Column(columnDefinition = "varchar(255) not null")
    private String message;

    @Column(columnDefinition = "int not null")
    private Integer user_id;

    @Column(columnDefinition = "boolean")
    private Boolean Status;

    @Column(columnDefinition = "int")
    private Integer decision_maker;

    @Column(columnDefinition = "int not null")
    private Integer product_id;
}
