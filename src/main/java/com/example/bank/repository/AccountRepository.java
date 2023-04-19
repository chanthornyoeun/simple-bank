package com.example.bank.repository;

import com.example.bank.models.BankAccount;
import com.example.bank.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<BankAccount, Long> {

    List<BankAccount> findAllByCustomer(Customer customer);

    long countAllByCustomer(Customer customer);

}
