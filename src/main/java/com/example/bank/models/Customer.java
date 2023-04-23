package com.example.bank.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity(name = "customers")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "bankAccounts"})
public class Customer extends AuditMetadata implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(length = 20)
    private String phone;
    @Column(length = 50)
    private String email;
    private String address;
    @OneToMany(mappedBy = "customer")
    private Set<BankAccount> bankAccounts;

}
