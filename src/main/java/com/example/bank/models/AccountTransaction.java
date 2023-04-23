package com.example.bank.models;

import com.example.bank.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity(name = "account_transactions")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AccountTransaction extends AuditMetadata implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private Date date = new Date();
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(nullable = false)
    private double balance;
    @ManyToOne
    private BankAccount bankAccount;

}
