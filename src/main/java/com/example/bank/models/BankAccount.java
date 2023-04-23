package com.example.bank.models;

import com.example.bank.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity(name = "bank_accounts")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "accountTransactions"})
public class BankAccount extends AuditMetadata implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Customer customer;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    AccountType accountType;
    @Column(nullable = false, length = 10, unique = true)
    String accountCode;
    @Column(nullable = false)
    Date openingDate;
    @Column(nullable = false, precision = 2)
    double balance;
    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    Set<AccountTransaction> accountTransactions;

}
