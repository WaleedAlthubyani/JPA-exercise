package org.example.ecommercewebsite.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Check(constraints = "length(password)>=7 and (role='admin' or role='customer') and balance>=0")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Please enter the user's username")
    @Size(min = 6,message = "Username must be more than 5 characters in length")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String username;

    @NotEmpty(message = "Please enter the user's password")
    @Size(min = 7)
    @Pattern(regexp = "^[a-zA-Z0-9]{7,}$")
    @Column(columnDefinition = "varchar(50) not null")
    private String password;

    @NotEmpty(message = "Please enter the user's email")
    @Email(message = "Please enter a valid email address")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String email;

    @NotEmpty(message = "Please enter the user's role")
    @Pattern(regexp = "^(?i)(admin|customer)$",message = "User can only be Admin or Customer")
    @Column(columnDefinition = "varchar(8) not null")
    private String role;

    @Positive(message = "balance must be a positive number")
    @Column(columnDefinition = "double not null")
    private Double balance;
}
